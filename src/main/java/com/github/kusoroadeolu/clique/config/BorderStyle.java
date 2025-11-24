package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.style.StyleBuilder;

/**
 * A class for styling table borders
 * */
public class BorderStyle {
    private final AnsiCode[] verticalStyle;
    private final AnsiCode[] horizontalStyle;
    private final AnsiCode[] edgeStyle;
    private final StyleBuilder styleBuilder;

    @Deprecated(since = "1.2.1", forRemoval = true)
    public static BorderStyle builder(){
        return new BorderStyle();
    }

    private BorderStyle(){
        this(new BorderStyleBuilder());
    }

    private BorderStyle(BorderStyleBuilder builder) {
        this.verticalStyle = builder.verticalStyle;
        this.horizontalStyle = builder.horizontalStyle;
        this.edgeStyle = builder.edgeStyle;
        this.styleBuilder = Clique.styleBuilder();
    }

    public static BorderStyleBuilder immutableBuilder(){
        return new BorderStyleBuilder();
    }

    public StyleBuilder styleBuilder(){
        return this.styleBuilder;
    }

    public AnsiCode[] getHorizontalBorderStyles() {
        return this.horizontalStyle.clone();
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BorderStyle horizontalBorderStyles(AnsiCode... horizontalStyles) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public AnsiCode[] getEdgeBorderStyles() {
        return this.edgeStyle.clone();
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BorderStyle edgeBorderStyles(AnsiCode... edgeStyles) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public AnsiCode[] getVerticalBorderStyles() {
        return this.verticalStyle.clone();
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BorderStyle verticalBorderStyles(AnsiCode... verticalStyles) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }


    public static class BorderStyleBuilder{
        private AnsiCode[] verticalStyle;
        private AnsiCode[] horizontalStyle;
        private AnsiCode[] edgeStyle;

        public BorderStyleBuilder verticalBorderStyles(AnsiCode... styles) {
            this.verticalStyle = styles;
            return this;
        }

        public BorderStyleBuilder horizontalBorderStyles(AnsiCode... styles) {
            this.horizontalStyle = styles;
            return this;
        }

        public BorderStyleBuilder edgeBorderStyles(AnsiCode... styles) {
            this.edgeStyle = styles;
            return this;
        }

        public BorderStyle build() {
            return new BorderStyle(this);
        }
    }
}
