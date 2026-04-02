package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;
import io.github.kusoroadeolu.clique.core.structures.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.ansi.StyleCode.RESET;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.applyAnsiToBorders;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseToCellIfPresent;

@InternalApi(since = "3.2.0")
public class DefaultFrame implements  Frame {
    private final List<FrameNode> nodes;
    private final FrameConfiguration configuration;
    private final BoxType type;
    private String title;
    private FrameAlign titleAlign;
    private int width;
    private final BorderChars borderChars;
    private static final int NO_WIDTH_SET = 0;
    private static final String NULL_FRAME_ALIGN = "DefaultFrame align cannot be null";

    public DefaultFrame(FrameConfiguration configuration, BoxType type) {
        Objects.requireNonNull(configuration, "Configuration cannot be null");
        Objects.requireNonNull(type, "DefaultFrame type cannot be null");
        this.nodes = new ArrayList<>();
        this.configuration = configuration;
        this.type = type;
        this.title = EMPTY;
        this.titleAlign = FrameAlign.CENTER;
        this.width = NO_WIDTH_SET;
        borderChars = BorderChars.from(type);
        this.styleBorders(borderChars);
    }

    public DefaultFrame() {
        this(FrameConfiguration.DEFAULT, BoxType.ROUNDED);
    }

    public DefaultFrame(BoxType type) {
        this(FrameConfiguration.DEFAULT, type);
    }

    public DefaultFrame(FrameConfiguration configuration) {
        this(configuration, BoxType.ROUNDED);
    }

    @Override
    public Frame title(String title, FrameAlign titleAlign) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(titleAlign, NULL_FRAME_ALIGN);
        this.title = title;
        this.titleAlign = titleAlign;
        return this;
    }

    @Override
    public Frame title(String title) {
        return title(title, FrameAlign.CENTER);
    }

    @Override
    public Frame width(int width) {
        if (width <= 0) throw new InvalidDimensionException(
                "Frame width must be greater than zero, got: %d".formatted(width)
        );        this.width = width;
        return this;
    }

    @Override
    public Frame nest(String str) {
        return nest(str, configuration.getFrameAlign());
    }

    @Override
    public Frame nest(String str, FrameAlign align) {
        Objects.requireNonNull(str, "String cannot be null");
        Objects.requireNonNull(align, NULL_FRAME_ALIGN);
        if (configuration.getParser() != null) str = str + RESET;
        nodes.add(new FrameNode.StringNode(str, align, configuration.getParser()));
        return this;
    }

    @Override
    public Frame nest(Component component) {
        return nest(component, configuration.getFrameAlign());
    }

    @Override
    public Frame nest(Component component, FrameAlign align) {
        Objects.requireNonNull(component, "Component cannot be null");
        Objects.requireNonNull(align, NULL_FRAME_ALIGN);
        nodes.add(new FrameNode.ComponentNode(component, align));
        return this;
    }

    public String get() {
        var appendedTitle = title;

        //If parser is not null
        if (configuration.getParser() != null && !title.isEmpty()) appendedTitle = title + RESET; //Add a reset flag to prevent title colors from bleeding


        var parsedTitle = parseToCellIfPresent(appendedTitle, configuration.getParser());
        int titleWidth = parsedTitle.width() + 2;
        int nodesMaxWidth = findNodesMaxWidth(); //Max content width

        int givenWidth = (noWidthSet() ? nodesMaxWidth + (configuration.getPadding() * 2) : this.width);

        if (noWidthSet() && !parsedTitle.isBlank()) {
            givenWidth = Math.max(givenWidth, titleWidth);
        }

        int availableWidth = givenWidth - (configuration.getPadding() * 2);
        if (!parsedTitle.isBlank()) validateTitleWidth(titleWidth, givenWidth);

        if (nodesMaxWidth > availableWidth) {
            throw new InvalidDimensionException(
                    "Content width (%d) exceeds available frame width (%d). Either increase frame width to at least %d or reduce content size."
                            .formatted(nodesMaxWidth, availableWidth, nodesMaxWidth + (configuration.getPadding() * 2))
            );
        }

        var sb = new StringBuilder();
        appendTitleToBox(parsedTitle, givenWidth, titleWidth, sb); //Using given width, not available width here, since avail width is meant for content not title

        for (FrameNode node : nodes) {
            align(node, availableWidth  , sb);
        }

        sb.append(borderChars.bottomLeft())
                .append(borderChars.hLine().repeat(givenWidth))
                .append(borderChars.bottomRight())
                .append(NEWLINE);

        return sb.toString();
    }

    //Given width = well the given width, avail width = width - (padding * 2), in the case of no width set, the avail width = max node width, given width = (width) + (padding * 2)
    void align(FrameNode node ,int availableWidth, StringBuilder sb) {
        var borderChar = this.borderChars;
        var padding = configuration.getPadding();
        int contentWidth = node.maxWidth();

        String fixed = BLANK.repeat(padding);

        int rem = availableWidth - contentWidth; //Given avail width = 6, content width = 5, we should append fixed, then the remainder = avail width
        for (var line : node.lines()) {
            String content = line.styledText();
            switch (node.align()){
                case RIGHT -> sb.append(borderChar.vLine())
                        .append(fixed)
                        .append(BLANK.repeat(rem))
                        .append(content)
                        .append(fixed)
                        .append(borderChar.vLine())
                        .append(NEWLINE);

                case LEFT -> sb.append(borderChar.vLine())
                        .append(fixed)
                        .append(content)
                        .append(BLANK.repeat(rem))
                        .append(fixed)
                        .append(borderChar.vLine())
                        .append(NEWLINE);

                case CENTER -> {
                    int leftPad = rem / 2;
                    int rightPad = rem - leftPad;
                    sb.append(borderChar.vLine())
                            .append(fixed)
                            .append(BLANK.repeat(leftPad))
                            .append(content)
                            .append(BLANK.repeat(rightPad))
                            .append(fixed)
                            .append(borderChar.vLine())
                            .append(NEWLINE);
                }
            }
        }
    }

    void appendTitleToBox(Cell parsedTitle, int givenWidth, int titleWidth, StringBuilder sb){
        if (!parsedTitle.isBlank()) {
            int leftWidth = findTitleBlockOffset(givenWidth, titleWidth, titleAlign);
            sb.append(borderChars.topLeft())
                    .append(borderChars.hLine().repeat(leftWidth))
                    .append(BLANK).append(parsedTitle.styledText()).append(BLANK)
                    .append(borderChars.hLine().repeat(givenWidth - titleWidth - leftWidth))
                    .append(borderChars.topRight())
                    .append(NEWLINE);
        } else {
            sb.append(borderChars.topLeft())
                    .append(borderChars.hLine().repeat(givenWidth))
                    .append(borderChars.topRight())
                    .append(NEWLINE);
        }
    }

    int findNodesMaxWidth() {
        return nodes.stream()
                .mapToInt(FrameNode::maxWidth)
                .max()
                .orElse(ZERO);
    }

    static int findTitleBlockOffset(int innerWidth, int contentWidth, FrameAlign align) {
        return switch (align) {
            case LEFT -> 1;
            case RIGHT -> (innerWidth - contentWidth) - 1; //Just
            case CENTER -> (innerWidth - contentWidth) / 2;
        };
    }

    boolean noWidthSet(){
        return this.width == NO_WIDTH_SET;
    }

    void validateTitleWidth(int titleWidth, int resolvedWidth) {
        if (titleWidth > resolvedWidth)
            throw new InvalidDimensionException(
                    "Title width (%d) exceeds frame width (%d). Increase frame width to at least %d or shorten the title."
                            .formatted(titleWidth, resolvedWidth, titleWidth)
            );
    }


    void customizeCorner(char corner) {
        var str = String.valueOf(corner);
        if (!str.isBlank()){
            borderChars.setCorners(str);
        }
    }

    void customizeVLine(char vLine) {
        var str = Character.toString(vLine);
        if (!str.isBlank()){
            borderChars.setVLine(str);
        }
    }

    void customizeHLine(char hLine) {
        var str = Character.toString(hLine);
        if (!str.isBlank()){
            borderChars.setHLine(str);
        }
    }

    void styleBorders(BorderChars borderChar) {
        final BorderStyle borderStyle = this.configuration.getBorderStyle();
        if (borderStyle != null) {
            applyAnsiToBorders(borderChar, borderStyle);
            customizeHLine(borderStyle.getHorizontalChar());
            customizeVLine(borderStyle.getVerticalChar());
            customizeCorner(borderStyle.getCornerChar());
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        DefaultFrame frame = (DefaultFrame) object;
        return width == frame.width && Objects.equals(nodes, frame.nodes) && Objects.equals(configuration, frame.configuration) && type == frame.type && Objects.equals(title, frame.title) && titleAlign == frame.titleAlign && Objects.equals(borderChars, frame.borderChars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, nodes, configuration, type, title, titleAlign, borderChars);
    }
}