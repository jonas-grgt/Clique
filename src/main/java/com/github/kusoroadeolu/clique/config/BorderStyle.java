package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.style.StyleBuilder;

/**
 * A class for styling table borders
 * */
public class BorderStyle {
    private AnsiCode[] verticalStyle;
    private AnsiCode[] horizontalStyle;
    private AnsiCode[] edgeStyle;
    private static final StyleBuilder styleBuilder = Clique.styleBuilder();

    public static BorderStyle builder(){
        return new BorderStyle();
    }

    public static StyleBuilder styleBuilder(){
        return styleBuilder;
    }

    public AnsiCode[] getHorizontalBorderStyles() {
        return horizontalStyle;
    }

    public BorderStyle horizontalBorderStyles(AnsiCode... horizontalStyles) {
        this.horizontalStyle = horizontalStyles;
        return this;
    }

    public AnsiCode[] getEdgeBorderStyles() {
        return edgeStyle;
    }

    public BorderStyle edgeBorderStyles(AnsiCode... edgeStyles) {
        this.edgeStyle = edgeStyles;
        return this;
    }

    public AnsiCode[] getVerticalBorderStyles() {
        return verticalStyle;
    }

    public BorderStyle verticalBorderStyles(AnsiCode... verticalStyles) {
        this.verticalStyle = verticalStyles;
        return this;
    }
}
