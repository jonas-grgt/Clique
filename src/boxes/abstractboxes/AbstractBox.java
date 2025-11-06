package boxes.abstractboxes;

import boxes.configuration.BoxConfiguration;
import boxes.exceptions.InvalidDimensionException;
import boxes.interfaces.Box;
import core.misc.Cell;
import core.misc.interfaces.Renderable;
import core.utils.BoxUtils;
import core.utils.StringUtils;
import core.utils.TableUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.utils.BoxUtils.splitPreservingAnsi;
import static core.utils.BoxUtils.validateDimensions;
import static core.utils.StringUtils.*;
import static core.utils.TableUtils.BLANK;
import static java.lang.Math.max;


public abstract class AbstractBox implements Box, Renderable {
    protected int width;
    protected int length;
    protected Cell boxContent;
    protected List<Cell> wordWrap;
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


    public AbstractBox(int width, int length, String content){
        this.width = width;
        this.length = length;
        this.vLine = "|";
        this.hLine = "-";
        this.edge = "+";
        this.wordWrap = new ArrayList<>();
        this.boxConfiguration = BoxConfiguration.builder();
        this.boxContent = StringUtils.parseCell(content, this.boxConfiguration.getParser());
    }

    public Box configuration(BoxConfiguration boxConfiguration){
        this.boxConfiguration = boxConfiguration;
        return this;
    }

    public Box width(int width) {
        this.width = width;
        return this;
    }


    public Box content(String content) {
        this.boxContent = StringUtils.parseCell(content, this.boxConfiguration.getParser());
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

        this.wordWrap.clear();
        this.adjust();


        final int maxCharsPerLine = this.width - (this.boxConfiguration.getCenterPadding() * 2);

        // Split original text (for calculations)
        final String originalContent = this.boxContent.text();
        final String styledContent = this.boxContent.styledText();
        final String[] originalWords = filterWhitespace(originalContent.split("\\s+"));
        final String[] styledWords = filterWhitespace(splitPreservingAnsi(styledContent));


        final StringBuilder currentOriginal = new StringBuilder();
        final StringBuilder currentStyled = new StringBuilder();


        for (int i = 0; i < originalWords.length; i++) {
            final String originalWord = originalWords[i];
            final String styledWord = styledWords[i];

            // Check length using original word
            if ((currentOriginal.length() + originalWord.length()) + 1 > maxCharsPerLine) {
                wrapLongString(currentOriginal, currentStyled, this.wordWrap ,maxCharsPerLine);
                if (!currentOriginal.isEmpty()) {
                    // Store the cell
                    this.wordWrap.add(new Cell(currentOriginal.toString(), currentStyled.toString()));
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

        // Add the last line
        if (!currentOriginal.isEmpty()) {
            wrapLongString(currentOriginal, currentStyled, this.wordWrap, maxCharsPerLine);
            this.wordWrap.add(new Cell(currentOriginal.toString(), currentStyled.toString()));
        }

        if(this.boxConfiguration.getAutoSize() && this.length < this.wordWrap.size()){
            this.length = this.wordWrap.size() + 2; //Add 2 to include the both borders
            return;
        }

        if (this.length < this.wordWrap.size()){
            throw new InvalidDimensionException("The length of this box is too little. Try enabling auto size");
        }
    }




    protected void adjust(){
        final String originalContent = this.boxContent.text();

        final String[] originalWords = filterWhitespace(originalContent.split("\\s+"));
        final int longest = getLongest(originalWords);
        if(this.boxConfiguration.getAutoSize()){
            this.width = max(this.width, longest) + (this.boxConfiguration.getCenterPadding() * 2);
        }else{
            validateDimensions(this.width, this.length);
        }

    }

    public void render(){
        System.out.println(this.buildBox());
    }

}