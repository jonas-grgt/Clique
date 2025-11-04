package boxes;

import boxes.enums.TextAlign;
import tables.structures.Cell;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.BoxUtils.alignText;
import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.BLANK;

public abstract class AbstractBox {
    protected int width;
    protected int length;
    protected Cell boxContent;
    protected List<String> wordWrap;
    private String vLine;
    private String hLine;
    private String edge;
    protected BoxConfiguration boxConfiguration;


    public AbstractBox(int width, int length, String content){
        this.width = width;
        this.length = length;
        this.vLine = "|";
        this.hLine = "-";
        this.edge = "+";
        this.wordWrap = new ArrayList<>();
        this.boxConfiguration = new BoxConfiguration();
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

    public void wrapWord(){
        final String content = this.boxContent.text();
        if(this.boxContent == null || content.isBlank()){
            return;
        }

        final int maxCharsPerLine = this.width - (this.boxConfiguration.getCenterPadding() * 2);
        final String[] words = content.split("\\s+");
        final String[] styledWords = boxContent.styledText().split("\\s+");
        final StringBuilder currentLine = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            final String word = words[i];
            final String styled = styledWords[i];
            if (currentLine.length() + word.length() + 1 > maxCharsPerLine) {
                // Line would be too long, save current line and start new one
                if (!currentLine.isEmpty()) {
                    this.wordWrap.add(currentLine.toString());
                    clearStringBuilder(currentLine);
                }
            }

            if (!currentLine.isEmpty()) {
                currentLine.append(BLANK);
            }

            currentLine.append(styled);
        }

        // Add the last line
        if (!currentLine.isEmpty()) {
            this.wordWrap.add(currentLine.toString());
        }

        this.wordWrap.forEach(System.out::println);

    }

    public void buildBox() {
        this.wrapWord();
        final StringBuilder sb = new StringBuilder();
        final String spaces = BLANK.repeat(this.width - 2);
        final String hLines = sb.repeat(this.hLine, this.width - 2).toString();
        final TextAlign textAlign = this.boxConfiguration.getTextAlign();
        clearStringBuilder(sb);
        sb.append(this.edge).append(hLines).append(this.edge).append("\n");


        int startLine = 1;
        final int availableLines = this.length - 2;
        final int textLines = this.wordWrap.size();

        if(textAlign == TextAlign.CENTER || textAlign == TextAlign.CENTER_LEFT || textAlign == TextAlign.CENTER_RIGHT){
            startLine = 1 + (availableLines - textLines) / 2;
        } else if(textAlign == TextAlign.BOTTOM_LEFT || textAlign == TextAlign.BOTTOM_CENTER || textAlign == TextAlign.BOTTOM_RIGHT){
            startLine = 1 + (availableLines - textLines);
        }

        for (int i = 1; i < this.length - 1; i++){

            if(i >= startLine && i < (startLine + textLines)){
                int textIndex = i - startLine;
                alignText(sb, textIndex + 1, textAlign , spaces, this.wordWrap, this.vLine);
            } else {
                sb.append(vLine).append(spaces).append(vLine).append("\n");
            }
        }

        sb.append(this.edge).append(hLines).append(this.edge);
        System.out.println(sb);
    }
}
