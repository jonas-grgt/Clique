package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.core.utils.Constants;
import io.github.kusoroadeolu.clique.parser.ParserUtils;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A class for styling table borders
 */
public class BorderStyle implements BorderSpec{
    public final static BorderStyle DEFAULT = new BorderStyle();

    private final BorderColor borderColor;
    private final char cornerChar;
    private final char verticalChar;
    private final char horizontalChar;
    private final boolean modifiedChar;

    private BorderStyle() {
        this(new BorderStyleBuilder());
    }


    private BorderStyle(BorderStyleBuilder builder) {
        borderColor = builder.borderColor;
        this.cornerChar = builder.cornerChar;
        this.verticalChar = builder.verticalChar;
        this.horizontalChar = builder.horizontalChar;
        this.modifiedChar = builder.modifiedChar;
    }

    public static BorderStyleBuilder builder(){
        return new BorderStyleBuilder();
    }

    public static BorderStyle fromSpec(BorderSpec spec){
        return switch (spec){
            case BorderStyle bs -> bs;
            case BorderColor bc -> fromBorderColor(bc);
            default -> builder().uniformStyle(spec.styles()).build();
        };
    }

    static BorderStyle fromBorderColor(BorderColor color){
        return builder().verticalStyle(color.getVerticalStyle())
                .horizontalStyle(color.getHorizontalStyle())
                .cornerStyle(color.getCornerStyle())
                .build();
    }

    /**
     * @deprecated As of 3.1.3, use {@link BorderStyle#builder()} instead. This will be removed in a future release.
     * */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static BorderStyleBuilder immutableBuilder() {
        return builder();
    }

    public boolean hasModifiedChar(){
        return modifiedChar;
    }

    /**
     * @return An empty string
     * */
    public AnsiCode[] styles() {
        return borderColor.styles();
    }

    /** @deprecated Use {@link #getHorizontalStyle()} instead */
    @Deprecated(since = "3.1")
    public AnsiCode[] getHorizontalBorderStyles() {
        return this.getHorizontalStyle();
    }

    public AnsiCode[] getHorizontalStyle() {
        return borderColor.getHorizontalStyle();
    }


    public char getCornerChar() {
        return cornerChar;
    }

    public char getVerticalChar() {
        return verticalChar;
    }

    public char getHorizontalChar() {
        return horizontalChar;
    }

    /** @deprecated Use {@link #getCornerStyle()} instead */
    @Deprecated(since = "3.1")
    public AnsiCode[] getEdgeBorderStyles() {
        return this.getCornerStyle();
    }

    public AnsiCode[] getCornerStyle() {
        return borderColor.getCornerStyle();
    }


    /** @deprecated Use {@link #getVerticalStyle()} instead */
    @Deprecated(since = "3.1")
    public AnsiCode[] getVerticalBorderStyles() {
        return this.getVerticalStyle();
    }

    public AnsiCode[] getVerticalStyle() {
        return borderColor.getVerticalStyle();
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BorderStyle that = (BorderStyle) object;
        return cornerChar == that.cornerChar && verticalChar == that.verticalChar && horizontalChar == that.horizontalChar && borderColor.equals(that.borderColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cornerChar, verticalChar, horizontalChar, borderColor);
    }

    @Override
    public String toString() {
        return "BorderStyle[" +
                "borderColor=" + borderColor +
                ", cornerChar=" + cornerChar +
                ", verticalChar=" + verticalChar +
                ", horizontalChar=" + horizontalChar +
                ']';
    }

    public static class BorderStyleBuilder {
        private BorderColor.BorderColorBuilder builder = BorderColor.builder();
        private BorderColor borderColor;
        private boolean modifiedChar =  false;
        private char cornerChar = Constants.BLANK_CHAR;
        private char verticalChar = Constants.BLANK_CHAR;
        private char horizontalChar = Constants.BLANK_CHAR;

        /** @deprecated Use {@link #verticalStyle(AnsiCode...)} instead */
        @Deprecated(since = "3.1")
        public BorderStyleBuilder verticalBorderStyles(AnsiCode... styles) {
            return this.verticalStyle(styles);
        }

        public BorderStyleBuilder verticalStyle(AnsiCode... styles) {
            builder.verticalStyle(styles);
            return this;
        }

        public BorderStyleBuilder verticalStyle(String style) {
            builder.verticalStyle(style);
            return this;
        }

        /** @deprecated Use {@link #horizontalStyle(AnsiCode...)} instead */
        @Deprecated(since = "3.1")
        public BorderStyleBuilder horizontalBorderStyles(AnsiCode... styles) {
            return this.horizontalStyle(styles);
        }

        public BorderStyleBuilder horizontalStyle(AnsiCode... styles) {
            builder.horizontalStyle(styles);
            return this;
        }

        public BorderStyleBuilder horizontalStyle(String style) {
            builder.horizontalStyle(style);
            return this;
        }

        /** @deprecated Use {@link #cornerStyle(AnsiCode...)} instead */
        @Deprecated
        public BorderStyleBuilder edgeBorderStyles(AnsiCode... styles) {
            return this.cornerStyle(styles);
        }

        public BorderStyleBuilder cornerStyle(AnsiCode... styles) {
            builder.cornerStyle(styles);
            return this;
        }

        public BorderStyleBuilder cornerStyle(String style) {
            builder.cornerStyle(style);
            return this;
        }

        public BorderStyleBuilder uniformStyle(AnsiCode... styles) {
            builder.uniformStyle(styles);
            return this;
        }

        public BorderStyleBuilder uniformStyle(String style) {
            builder.uniformStyle(style);
            return this;
        }

        public BorderStyleBuilder cornerChar(char cornerChar){
            this.cornerChar = cornerChar;
            modifiedChar = true;
            return this;
        }

        public BorderStyleBuilder horizontalChar(char horizontalChar){
            this.horizontalChar = horizontalChar;
            modifiedChar = true;
            return this;
        }

        public BorderStyleBuilder verticalChar(char verticalChar){
            this.verticalChar = verticalChar;
            modifiedChar = true;
            return this;
        }

        public BorderStyle build() {
            borderColor = builder.build();
            return new BorderStyle(this);
        }
    }
}