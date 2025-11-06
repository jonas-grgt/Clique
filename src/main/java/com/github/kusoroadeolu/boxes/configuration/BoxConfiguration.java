package com.github.kusoroadeolu.boxes.configuration;

import com.github.kusoroadeolu.boxes.enums.TextAlign;
import com.github.kusoroadeolu.core.clique.Clique;
import com.github.kusoroadeolu.core.misc.BorderStyle;
import com.github.kusoroadeolu.parser.token.stringparser.interfaces.AnsiStringParser;

public class BoxConfiguration {
    private int centerPadding;
    private TextAlign textAlign;
    private AnsiStringParser parser;
    private BorderStyle borderStyle;
    private boolean autoSize;


    public int getCenterPadding() {
        return centerPadding;
    }

    public BoxConfiguration centerPadding(int centerPadding) {
        if(centerPadding < 0){
            centerPadding = 2;
        }
        this.centerPadding = centerPadding;
        return this;
    }

    public boolean getAutoSize() {
        return autoSize;
    }

    public BoxConfiguration autoSize(boolean autoSize) {
        this.autoSize = autoSize;
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
        this.autoSize = false;
    }
}
