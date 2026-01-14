package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.core.display.Renderable;
import com.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import com.github.kusoroadeolu.clique.core.utils.StringUtils;
import com.github.kusoroadeolu.clique.tables.structures.Cell;

import java.util.ArrayList;
import java.util.List;

import static com.github.kusoroadeolu.clique.core.utils.BoxUtils.splitPreservingAnsi;
import static com.github.kusoroadeolu.clique.core.utils.BoxUtils.validateDimensions;
import static com.github.kusoroadeolu.clique.core.utils.StringUtils.*;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.BLANK;
import static java.lang.Math.max;


public abstract class AbstractBox implements Box, Renderable {
    protected int width;
    protected int length;
    protected Cell boxContent;
    protected List<Cell> contentWrap;
    protected String vLine;
    protected String hLine;
    protected String edge;
    protected BoxConfiguration boxConfiguration;

    public AbstractBox(){
        this(0, 0, "");
    }

    public AbstractBox(BoxConfiguration configuration){
        this(0, 0, "");
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
        this.boxConfiguration = BoxConfiguration.immutableBuilder().build();
        this.boxContent = parseCell(content, this.boxConfiguration.getParser());
    }


    public Box width(int width) {
        this.width = width;
        return this;
    }

    public Box content(String content) {
        this.boxContent = parseCell(content, this.boxConfiguration.getParser());
        return this;
    }

    public Box length(int length) {
        this.length = length;
        return this;
    }

    protected void wrapWord(){
        if(this.boxContent == null || this.boxContent.text().isBlank() && !this.boxConfiguration.getAutoSize()){
            return;
        }

        this.contentWrap.clear();
        this.adjust();

        final int maxCharsPerLine = this.width - (this.boxConfiguration.getCenterPadding() * 2);

        final String originalContent = this.boxContent.text();
        final String styledContent = this.boxContent.styledText();
        final String[] originalLines = originalContent.split(StringUtils.NEWLINE.pattern(), -1);
        final String[] styledLines = styledContent.split(StringUtils.NEWLINE.pattern(), -1);

        // Process each line independently
        for (int i = 0; i < originalLines.length; i++) {
            final String originalLine = originalLines[i];
            final String styledLine = styledLines[i];

            if (originalLine.isEmpty()) {
                this.contentWrap.add(new Cell("", ""));
                continue;
            }

            // Split line into words
            final String[] originalWords = filterWhitespace(originalLine.split(SPACES.pattern()));
            final String[] styledWords = filterWhitespace(splitPreservingAnsi(styledLine));

            final StringBuilder currentOriginal = new StringBuilder();
            final StringBuilder currentStyled = new StringBuilder();

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


    protected void adjust(){
        final String originalContent = this.boxContent.text();
        final int longest = getDynamicCharsPerLine(originalContent);
        if(this.boxConfiguration.getAutoSize()){
            this.width = max(this.width, longest) + (this.boxConfiguration.getCenterPadding() * 2);
        }
        else validateDimensions(this.width, this.length);


    }

    public void render(){
        System.out.println(this.buildBox());
    }


}