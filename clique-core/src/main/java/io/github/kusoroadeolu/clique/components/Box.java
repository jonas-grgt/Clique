package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.configuration.BoxConfiguration;
import io.github.kusoroadeolu.clique.configuration.BoxType;
import io.github.kusoroadeolu.clique.configuration.TextAlign;
import io.github.kusoroadeolu.clique.internal.BorderChars;
import io.github.kusoroadeolu.clique.internal.BoxWrapper;
import io.github.kusoroadeolu.clique.internal.WidthAwareList;
import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.internal.exception.InvalidDimensionException;
import io.github.kusoroadeolu.clique.internal.utils.StringUtils;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.internal.utils.BoxUtils.applyAnsiToBorders;
import static io.github.kusoroadeolu.clique.internal.utils.BoxUtils.drawBox;
import static java.util.Objects.requireNonNull;

@InternalApi(since = "3.2.0")
public class Box implements Component {
    private int width;
    private int height; //If width and height == 0
    private String content;
    private String cachedString = null;
    private final BoxConfiguration configuration;
    private TextAlign align = null; //Takes precedence over box config only if not null
    private final BorderChars borderChars;
    private static final String BOX_CONTENT_NOT_NULL = "Box content cannot be null";
    private static final String TEXT_ALIGN_NOT_NULL = "Text align cannot be null";

    public Box(BoxType type, BoxConfiguration configuration) {
        validateTypeAndConfig(type, configuration);
        this.borderChars = BorderChars.from(type);
        this.configuration = configuration;
        this.styleBorders();
    }

    public Box(BoxType type) {
        this(type, BoxConfiguration.DEFAULT);
    }

    public Box(BoxConfiguration configuration) {
        this(BoxType.ROUNDED, configuration);
    }

    public Box() {
        this(BoxConfiguration.DEFAULT);
    }

    public String get() {
        if (cachedString != null) return cachedString;
        WidthAwareList cells = resolveLines();
        this.resolveDimensions(cells);
        TextAlign ta = this.align == null ? configuration.getTextAlign() : this.align;


        final var chars = this.borderChars;
        final StringBuilder sb = new StringBuilder();
        final BoxWrapper wrapper = new BoxWrapper(
                this.width, this.height, this.configuration,
                cells.cells(), chars.hLine(), chars.vLine(),
                chars.topLeft(), chars.topRight(), chars.bottomRight(), chars.bottomLeft()
        );
        drawBox(sb, wrapper, ta);
        return (cachedString = sb.toString());
    }

    public Box content(String content) {
        Objects.requireNonNull(content, BOX_CONTENT_NOT_NULL);
        this.content = content;
        nullCachedString();
        return this;
    }

    public Box content(String content, TextAlign align) {
        Objects.requireNonNull(content, BOX_CONTENT_NOT_NULL);
        Objects.requireNonNull(align, TEXT_ALIGN_NOT_NULL);
        this.content = content;
        this.align = align;
        nullCachedString();
        return this;
    }

    public Box dimensions(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Width and height must be greater than 0. To skip dimensions and instead autosize, don't call this method."
            );
        }

        this.height = height;
        this.width = width;
        return this;
    }

    protected void resolveDimensions(WidthAwareList cells) {
        int padding = this.configuration.getPadding();

        if ((width == 0 && height == 0)) { //if width and height == 0 autosize() was called
            this.width = cells.longest() + (padding * 2);
            this.height = cells.size(); //Taking into account the top and bottom border
        } else {
            int usableWidth = this.width - (padding * 2);
            int contentWidth = cells.longest();
            int contentHeight = cells.size();

            if (contentWidth > usableWidth) throw new InvalidDimensionException("Content overflows: content is %s wide but usable inner width is only %s".formatted(this.width, contentWidth));
            if (contentHeight > height) throw new InvalidDimensionException("Content overflows: %s lines of content cannot fit in a box of height %s".formatted(this.height, contentHeight));
        }
    }

    //Splits the box content per newline, maps each chunk to a cell and encompasses them in a list
    WidthAwareList resolveLines(){
        if (content == null || content.isEmpty()) return new WidthAwareList();
        var parser = configuration.getParser();
        var cellList = content.lines()
                .map(s -> StringUtils.parseToCellIfPresent(s, parser))
                .toList();
        return new WidthAwareList(cellList);
    }

    private void nullCachedString() {
        cachedString = null;
    }

    void styleBorders() {
        if (configuration.getBorderColor() != null) {
            applyAnsiToBorders(borderChars, configuration.getBorderColor());
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Box that = (Box) object;
        return Objects.equals(borderChars, that.borderChars);
    }

    static void validateTypeAndConfig(BoxType type, BoxConfiguration config) {
        requireNonNull(type, "Box type cannot be null");
        requireNonNull(config, "Box configuration cannot be null");
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, content, configuration, align, borderChars);
    }

    @Override
    public String toString() {
        return "Box[" +
                "boxConfiguration=" + configuration +
                ", align=" + align +
                ", borderChars=" + borderChars +
                ", content='" + content + '\'' +
                ", height=" + height +
                ", width=" + width +
                ']';
    }
}

