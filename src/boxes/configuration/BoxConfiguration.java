package boxes.configuration;

import boxes.enums.TextAlign;
import core.clique.Clique;
import core.misc.BorderStyle;
import parser.token.stringparser.interfaces.AnsiStringParser;

public class BoxConfiguration {
    private int centerPadding;
    private TextAlign textAlign;
    private AnsiStringParser parser;
    private BorderStyle borderStyle;
    private boolean autoAdjustBox;


    public int getCenterPadding() {
        return centerPadding;
    }

    public BoxConfiguration centerPadding(int centerPadding) {
        this.centerPadding = centerPadding;
        return this;
    }

    public boolean getAutoAdjustBox() {
        return autoAdjustBox;
    }

    public BoxConfiguration autoAdjustBox(boolean autoAdjustBox) {
        this.autoAdjustBox = autoAdjustBox;
        return this;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public BoxConfiguration borderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
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

    public static BoxConfiguration builder(){
        return new BoxConfiguration();
    }

    private BoxConfiguration(){
        this.centerPadding = 2;
        this.textAlign = TextAlign.CENTER;
        this.parser = Clique.parser();
        this.borderStyle = null;
        this.autoAdjustBox = false;
    }
}
