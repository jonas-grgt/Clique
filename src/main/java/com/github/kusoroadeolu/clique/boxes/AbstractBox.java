package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import com.github.kusoroadeolu.clique.core.utils.Constants;
import com.github.kusoroadeolu.clique.tables.structures.Cell;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kusoroadeolu.clique.core.utils.BoxUtils.splitPreservingAnsi;
import static com.github.kusoroadeolu.clique.core.utils.BoxUtils.validateDimensions;
import static com.github.kusoroadeolu.clique.core.utils.Constants.*;
import static com.github.kusoroadeolu.clique.core.utils.StringUtils.*;
import static java.lang.Math.max;


public abstract class AbstractBox implements Box {
    public int width;
    public int length;
    protected Cell boxContent;
    protected List<Cell> contentWrap;
    protected String vLine;
    protected String hLine;
    protected String edge;
    protected BoxConfiguration boxConfiguration;

    public AbstractBox(){
        this(ZERO, ZERO, EMPTY);
    }

    public AbstractBox(BoxConfiguration configuration){
        this(ZERO, ZERO, EMPTY);
        this.boxConfiguration = configuration;
    }


    public Box configuration(BoxConfiguration boxConfiguration){
        this.boxConfiguration = boxConfiguration;
        return this;
    }

    public AbstractBox(int width, int length, String content){
        this.width = width;
        this.length = length;
        this.vLine = "|";
        this.hLine = "-";
        this.edge = "+";
        this.contentWrap = new ArrayList<>();
        this.boxConfiguration = BoxConfiguration.DEFAULT;
        this.boxContent = parseCell(content, this.boxConfiguration.getParser());
    }

    public Box content(String content) {
        this.boxContent = parseCell(content, this.boxConfiguration.getParser());
        return this;
    }

    protected void wrapWord(){
        if((this.boxContent == null || this.boxContent.text().isBlank()) && !this.boxConfiguration.getAutoSize()){
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
            final String[] styledWords = filterWhitespace(splitPreservingAnsi(styledLine));

            final var currentOriginal = new StringBuilder();
            final var currentStyled = new StringBuilder();

            for (int j = 0; j < originalWords.length; j++) {
                final String originalWord = originalWords[j];
                final String styledWord = styledWords[j];

                // Check if adding this word would overflow
                if ((currentOriginal.length() + originalWord.length()) + 1 > maxCharsPerLine) {
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

        if(this.boxConfiguration.getAutoSize() && this.length <= this.contentWrap.size()){
            this.length = this.contentWrap.size() + 2;
            return;
        }

        if (this.length < this.contentWrap.size()){
            throw new InvalidDimensionException("The length of this box is too little. Try enabling auto size");
        }
    }

    protected void adjustBox(){
        final String originalContent = this.boxContent.text();
        final int longest = getDynamicCharsPerLine(originalContent);
        if(this.boxConfiguration.getAutoSize()) this.width = max(this.width, longest) + (this.boxConfiguration.getCenterPadding() * 2);
        else validateDimensions(this.width, this.length);
    }

    public void render(PrintStream stream) {
        stream.println(this.get());
    }


    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        AbstractBox that = (AbstractBox) object;
        return width == that.width && length == that.length && Objects.equals(boxContent, that.boxContent) && vLine.equals(that.vLine) && hLine.equals(that.hLine) && edge.equals(that.edge) && Objects.equals(boxConfiguration, that.boxConfiguration);
    }

    public int hashCode() {
        return Objects.hash(width, length, boxContent, vLine, edge, boxConfiguration);
    }

    @Override
    public String toString() {
        return "Box[" +
                "width=" + width +
                ", length=" + length +
                ", content=" + boxContent +
                ", vLine='" + vLine + '\'' +
                ", hLine='" + hLine + '\'' +
                ", edge='" + edge + '\'' +
                ", boxConfiguration=" + boxConfiguration +
                ']';
    }

    public static class BoxDimensionBuilder{
        private final AbstractBox box;

        public BoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        public Box withDimensions(int width, int length){
            this.box.length = length;
            this.box.width = width;
            return box;
        }

        public Box noDimensions(){
            return this.withDimensions(0, 0);
        }
    }

    public static class CustomizableBoxDimensionBuilder{
        private final AbstractBox box;

        public CustomizableBoxDimensionBuilder(Box box) {
            this.box = (AbstractBox) box;
        }

        public CustomizableBox withDimensions(int width, int length){
            var bdb = new BoxDimensionBuilder(box);
            bdb.withDimensions(width, length);
            return (CustomizableBox) box;
        }

        public CustomizableBox noDimensions(){
            var bdb = new BoxDimensionBuilder(box);
            bdb.noDimensions();
            return (CustomizableBox) box;
        }
    }
}