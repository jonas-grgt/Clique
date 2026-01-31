package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Arrays;
import java.util.Objects;

/**
 * A class for styling table borders
 * */
public class BorderStyle {
    private final AnsiCode[] verticalStyle;
    private final AnsiCode[] horizontalStyle;
    private final AnsiCode[] edgeStyle;
    private final StyleBuilder styleBuilder;
    public final static BorderStyle DEFAULT = new BorderStyle();

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


    @Override
    public String toString() {
        return "BorderStyle[" +
                "verticalStyle=" + Arrays.toString(verticalStyle) +
                ", horizontalStyle=" + Arrays.toString(horizontalStyle) +
                ", edgeStyle=" + Arrays.toString(edgeStyle) +
                ", styleBuilder=" + styleBuilder +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BorderStyle that = (BorderStyle) object;
        return Arrays.equals(verticalStyle, that.verticalStyle) && Arrays.equals(horizontalStyle, that.horizontalStyle) && Arrays.equals(edgeStyle, that.edgeStyle) && styleBuilder.equals(that.styleBuilder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(verticalStyle), Arrays.hashCode(horizontalStyle), Arrays.hashCode(edgeStyle), styleBuilder);
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
