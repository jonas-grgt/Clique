package boxes.abstractboxes;

import boxes.Box;
import boxes.configuration.BoxConfiguration;
import core.misc.Cell;
import core.misc.interfaces.Renderable;
import core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static core.utils.BoxUtils.filterWhitespace;
import static core.utils.BoxUtils.splitPreservingAnsi;
import static core.utils.StringUtils.clearStringBuilder;
import static core.utils.TableUtils.BLANK;


public abstract class AbstractBox implements Box, Renderable {
    protected int width;
    protected int length;
    protected Cell boxContent;
    protected List<Cell> wordWrap;  // Changed to List<Cell> to store both styled and original
    protected String vLine;
    protected String hLine;
    protected String edge;
    protected BoxConfiguration boxConfiguration;

    public AbstractBox(){
        this(0, 0, "");
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

    public AbstractBox configuration(BoxConfiguration boxConfiguration){
        this.boxConfiguration = boxConfiguration;
        return this;
    }

    public AbstractBox width(int width) {
        this.width = width;
        return this;
    }


    public AbstractBox content(String content) {
        this.boxContent = StringUtils.parseCell(content, this.boxConfiguration.getParser());
        return this;
    }

    public AbstractBox length(int length) {
        this.length = length;
        return this;
    }

    protected void wrapWord(){
        if(this.boxContent == null || this.boxContent.text().isBlank()){
            return;
        }
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
            this.wordWrap.add(new Cell(currentOriginal.toString(), currentStyled.toString()));
        }

    }

    protected void autoAdjust(){
        if(!boxConfiguration.getAutoAdjustBox()){
            final String originalContent = this.boxContent.text();
            final String[] originalWords = filterWhitespace(originalContent.split("\\s+"));
            int longest = 0;
            for (String s : originalWords){
                if (s.length() > longest) {
                    longest = s.length();
                }
            }

            this.width = Math.max(this.width, longest) + (this.boxConfiguration.getCenterPadding() * 2);
            System.out.println(this.width);
        }
    }

    public void render(){
        System.out.println(this.buildBox());
    }


    public abstract String buildBox();
}