package boxes;

import boxes.enums.TextAlign;
import core.clique.Clique;
import parser.token.stringparser.interfaces.AnsiStringParser;

public class BoxConfiguration {
    private int centerPadding;
    private TextAlign textAlign;
    private AnsiStringParser parser;

    public int getCenterPadding() {
        return centerPadding;
    }

    public BoxConfiguration centerPadding(int centerPadding) {
        this.centerPadding = centerPadding;
        return this;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public BoxConfiguration textAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
        return this;
    }

    public AnsiStringParser getParser() {
        return parser;
    }

    public BoxConfiguration parser(AnsiStringParser parser) {
        this.parser = parser;
        return this;
    }

    public BoxConfiguration(){
        this.centerPadding = 2;
        this.textAlign = TextAlign.CENTER;
        this.parser = Clique.parser();
    }
}
