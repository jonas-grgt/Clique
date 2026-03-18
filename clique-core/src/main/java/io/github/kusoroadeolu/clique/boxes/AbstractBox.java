package io.github.kusoroadeolu.clique.boxes;


import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.core.utils.CharWidth;
import io.github.kusoroadeolu.clique.core.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.splitAndPreserveAnsi;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.validateDimensions;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.*;

public abstract class AbstractBox implements Box {
    int width;
    int height;
    Cell boxContent;
    String cachedString = null;
    List<Cell> contentWrap;
    BoxConfiguration boxConfiguration;

    public AbstractBox() {
        this(ZERO, ZERO);
    }

    AbstractBox(int width, int height) {
        this.width = width;
        this.height = height;
        this.contentWrap = new ArrayList<>();
        this.boxConfiguration = BoxConfiguration.DEFAULT;
    }

    public Box content(String content) {
        Objects.requireNonNull(content, "Box content cannot be null");
        this.boxContent = parseToCell(content, this.boxConfiguration.getParser());
        cachedString = null;
        return this;
    }

    @Override
    public Box content(Object object) {
        Objects.requireNonNull(object, "Object cannot be null");
        return this.content(object.toString());
    }

    protected void wrapWord() {
        if ((this.boxContent == null || this.boxContent.isBlank()) && !this.boxConfiguration.getAutoSize()) {
            return;
        }

        this.contentWrap.clear();
        this.adjustBox();

        final int maxCharsPerLine = this.width - (this.boxConfiguration.getPadding() * 2);

        final String originalContent = this.boxContent.text();
        final String styledContent = this.boxContent.styledText();
        final String[] originalLines = originalContent.split(Constants.NEWLINE_PATTERN.pattern(), -1);
        final String[] styledLines = styledContent.split(Constants.NEWLINE_PATTERN.pattern(), -1);

        // Process each line independently
        for (int i = 0; i < originalLines.length; i++) {
            final String originalLine = originalLines[i];
            final String styledLine = styledLines[i];
            if (originalLine.isEmpty()) {
                this.contentWrap.add(new Cell(Constants.EMPTY, Constants.EMPTY));
                continue;
            }

            // Split line into words
            final String[] originalWords = filterWhitespace(originalLine.split(SPACES_PATTERN.pattern()));
            final String[] styledWords = filterWhitespace(splitAndPreserveAnsi(styledLine));

            final var currentOriginal = new StringBuilder();
            final var currentStyled = new StringBuilder();

            for (int j = 0; j < originalWords.length; j++) {
                final String originalWord = originalWords[j];
                final String styledWord = styledWords[j];

                // Check if adding this word would overflow
                if ((CharWidth.of(currentOriginal.toString()) + CharWidth.of(originalWord)) + 1 > maxCharsPerLine) {
                    wrapLongString(currentOriginal, currentStyled, this.contentWrap, maxCharsPerLine);
                    if (!currentOriginal.isEmpty()) {
                        this.contentWrap.add(new Cell(currentOriginal.toString(), currentStyled.toString()));
                        clearStringBuilder(currentOriginal);
                        clearStringBuilder(currentStyled);
                    }
                }

                if (!currentOriginal.isEmpty()) {
                    currentOriginal.append(BLANK);
                    currentStyled.append(BLANK);
                }

                currentOriginal.append(originalWord);
                currentStyled.append(styledWord);
            }

            // Add the last line from this newline
            if (!currentOriginal.isEmpty()) {
                wrapLongString(currentOriginal, currentStyled, this.contentWrap, maxCharsPerLine);
                this.contentWrap.add(new Cell(currentOriginal.toString(), currentStyled.toString()));
            }
        }


        if (this.boxConfiguration.getAutoSize() && this.height <= this.contentWrap.size()) {
            this.height = this.contentWrap.size() + 2;
            return;
        }

        if (this.height < this.contentWrap.size()) {
            throw new InvalidDimensionException("The length of this box is too little. Try enabling auto size");
        }
    }

    protected void adjustBox() {
        final String originalContent = this.boxContent.text();
        final int longest = getLengthOfLongestString(originalContent);
        if (this.boxConfiguration.getAutoSize())
            this.width = Math.max(this.width, longest) + (this.boxConfiguration.getPadding() * 2);
        else validateDimensions(this.width, this.height);
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        AbstractBox that = (AbstractBox) object;
        return width == that.width && height == that.height && Objects.equals(boxContent, that.boxContent) && Objects.equals(contentWrap, that.contentWrap) && Objects.equals(boxConfiguration, that.boxConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, boxContent, contentWrap, boxConfiguration);
    }

    public static class BoxDimensionBuilder {
        private final AbstractBox box;

        public BoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        public Box withDimensions(int width, int height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException(
                        "Width and height must be greater than 0. To skip dimensions, enable autoSize() in BoxConfiguration and call noDimensions()."
                );
            }
            this.box.height = height;
            this.box.width = width;
            return box;
        }

        public Box noDimensions() {
            if (!this.box.boxConfiguration.getAutoSize())
                throw new IllegalStateException(
                        "noDimensions() requires autoSize to be enabled in BoxConfiguration"
                );
            this.box.width = 0;
            this.box.height = 0;
            return this.box;
        }
    }

    /**
     * @deprecated As of 3.1, use {@link BoxDimensionBuilder} instead. This class will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static class CustomizableBoxDimensionBuilder {
        private final AbstractBox box;

        public CustomizableBoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        public CustomizableBox withDimensions(int width, int height) {
            var bdb = new BoxDimensionBuilder(box);
            bdb.withDimensions(width, height);
            return (CustomizableBox) box;
        }

        public CustomizableBox noDimensions() {
            var bdb = new BoxDimensionBuilder(box);
            bdb.noDimensions();
            return (CustomizableBox) box;
        }
    }
}