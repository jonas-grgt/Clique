package io.github.kusoroadeolu.clique.boxes;


import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import io.github.kusoroadeolu.clique.core.utils.CharWidth;
import io.github.kusoroadeolu.clique.core.utils.Constants;
import io.github.kusoroadeolu.clique.core.structures.Cell;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.splitAndPreserveAnsi;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.validateDimensions;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.*;

public abstract class AbstractBox implements Box {
    protected int width;
    protected int height;
    protected Cell boxContent;
    protected List<Cell> contentWrap;
    protected String vLine;
    protected String hLine;
    protected String edge;
    protected BoxConfiguration boxConfiguration;

    public AbstractBox() {
        this(ZERO, ZERO, EMPTY);
    }

    AbstractBox(BoxConfiguration configuration) {
        this(ZERO, ZERO, EMPTY);
        this.boxConfiguration = configuration;
    }

    AbstractBox(int width, int height, String content) {
        this.width = width;
        this.height = height;
        this.vLine = "|";
        this.hLine = "-";
        this.edge = "+";
        this.contentWrap = new ArrayList<>();
        this.boxConfiguration = BoxConfiguration.DEFAULT;
        this.boxContent = parseCell(content, this.boxConfiguration.getParser());
    }

    public Box content(String content) {
        Objects.requireNonNull(content, "Box content cannot be null");
        this.boxContent = parseCell(content, this.boxConfiguration.getParser());
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

        final int maxCharsPerLine = this.width - (this.boxConfiguration.getCenterPadding() * 2);

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
            this.width = Math.max(this.width, longest) + (this.boxConfiguration.getCenterPadding() * 2);
        else validateDimensions(this.width, this.height);
    }

    @Override
    public void render(PrintStream stream) {
        stream.println(this.get());
    }


    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        AbstractBox that = (AbstractBox) object;
        return width == that.width && height == that.height && Objects.equals(boxContent, that.boxContent) && vLine.equals(that.vLine) && hLine.equals(that.hLine) && edge.equals(that.edge) && Objects.equals(boxConfiguration, that.boxConfiguration);
    }

    public int hashCode() {
        return Objects.hash(width, height, boxContent, vLine, edge, boxConfiguration);
    }

    @Override
    public String toString() {
        return "Box[" +
                "width=" + width +
                ", length=" + height +
                ", content=" + boxContent +
                ", vLine='" + vLine + '\'' +
                ", hLine='" + hLine + '\'' +
                ", edge='" + edge + '\'' +
                ", configuration=" + boxConfiguration +
                ']';
    }

    public static class BoxDimensionBuilder {
        private final AbstractBox box;

        public BoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        public Box withDimensions(int width, int height) {
            if (width < 0 || height < 0) throw new IllegalArgumentException("Width or Height cannot be negative");
            this.box.height = height;
            this.box.width = width;
            return box;
        }

        public Box noDimensions() {
            return this.withDimensions(0, 0);
        }
    }

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