package io.github.kusoroadeolu.clique.boxes;


import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.config.TextAlign;
import io.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import io.github.kusoroadeolu.clique.core.structures.WidthAwareList;
import io.github.kusoroadeolu.clique.core.utils.StringUtils;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.ZERO;

public abstract class AbstractBox implements Box {
    int width;
    int height; //If width and height == 0
    String boxContent;
    String cachedString = null;
    WidthAwareList cells;
    BoxConfiguration boxConfiguration;
    TextAlign align = null; //Takes precedence over box config only if not null

    private static final String BOX_CONTENT_NOT_NULL = "Box content cannot be null";
    private static final String OBJECT_NOT_NULL = "Object given cannot be null";
    private static final String TEXT_ALIGN_NOT_NULL = "Text align cannot be null";


    AbstractBox() {
        this(ZERO, ZERO);
    }

    AbstractBox(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new WidthAwareList();
        this.boxConfiguration = BoxConfiguration.DEFAULT;
    }

    @Override
    public Box content(String content) {
        Objects.requireNonNull(content, BOX_CONTENT_NOT_NULL);
        this.boxContent = content;
        nullCachedString();
        return this;
    }

    @Override
    public Box content(Object object) {
        Objects.requireNonNull(object, OBJECT_NOT_NULL);
        return this.content(object.toString());
    }

    @Override
    public Box content(String content, TextAlign align) {
        Objects.requireNonNull(content, BOX_CONTENT_NOT_NULL);
        Objects.requireNonNull(align, TEXT_ALIGN_NOT_NULL);
        this.boxContent = content;
        this.align = align;
        nullCachedString();
        return this;
    }

    @Override
    public Box content(Object object, TextAlign align) {
        Objects.requireNonNull(object, OBJECT_NOT_NULL);
        return content(object.toString(), align);
    }


    protected void resolveDimensions(WidthAwareList cells) {
        var config = this.boxConfiguration;
        int padding = config.getPadding();

        if (config.getAutoSize() || (width == 0 && height == 0)) { //if width and height == 0 autosize() was called
            this.width = cells.longest() + (padding * 2);
            this.height = cells.size(); //Taking into account the top and bottom border
        } else {
            int usableWidth = this.width - (padding * 2);
            int contentWidth = cells.longest();
            int contentHeight = cells.size();

            if (contentWidth > usableWidth) throw new InvalidDimensionException("Width: %s is less than max width of string %s".formatted(this.width, contentWidth));
            if (contentHeight > height) throw new InvalidDimensionException("Height: %s is less than max height of string %s".formatted(this.height, contentHeight));
        }
    }

    //Splits the box content per newline, maps each chunk to a cell and encompasses them in a list
    WidthAwareList resolveLines(){
        var parser = boxConfiguration.getParser();
        var cellList = boxContent.lines()
                .map(s -> StringUtils.parseToCell(s, parser))
                .toList();
        return new WidthAwareList(cellList);
    }



    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        AbstractBox that = (AbstractBox) object;
        return width == that.width && height == that.height && Objects.equals(boxContent, that.boxContent) && Objects.equals(cachedString, that.cachedString) && Objects.equals(cells, that.cells) && Objects.equals(boxConfiguration, that.boxConfiguration) && align == that.align;
    }

    void nullCachedString(){
        cachedString = null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, boxContent, cachedString, cells, boxConfiguration, align);
    }

    public static class BoxDimensionBuilder {
        private final AbstractBox box;

        public BoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        public Box withDimensions(int width, int height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException(
                        "Width and height must be greater than 0. To skip dimensions, use the autosize() method instead."
                );
            }

            this.box.height = height;
            this.box.width = width;
            return box;
        }

        /**
         * @deprecated in favor of {@link BoxDimensionBuilder#autosize()} method . This will be removed in a future release
         * */
        @Deprecated(since = "3.1.3", forRemoval = true)
        public Box noDimensions() {
           return autosize();
        }

        public Box autosize(){
            this.box.width = 0;
            this.box.height = 0;
            return this.box;
        }


    }

    /**
     * @deprecated in favor of {@link io.github.kusoroadeolu.clique.config.BorderStyle} customization methods. This will be removed in the future.
     * */
    @Deprecated(forRemoval = true, since = "3.1")
    public static class CustomizableBoxDimensionBuilder {
        private final AbstractBox box;

        public CustomizableBoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        CustomizableBox withDimensions(int width, int height) {
            var bdb = new BoxDimensionBuilder(box);
            bdb.withDimensions(width, height);
            return (CustomizableBox) box;
        }

        CustomizableBox noDimensions() {
            var bdb = new BoxDimensionBuilder(box);
            bdb.noDimensions();
            return (CustomizableBox) box;
        }
    }
}