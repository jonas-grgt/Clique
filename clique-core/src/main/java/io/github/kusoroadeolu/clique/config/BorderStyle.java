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

    private final AnsiCode[] verticalStyle;
    private final AnsiCode[] horizontalStyle;
    private final AnsiCode[] edgeStyle;
    private final char cornerChar;
    private final char verticalChar;
    private final char horizontalChar;
    private final boolean modifiedChar;

    private BorderStyle() {
        this(new BorderStyleBuilder());
    }


    private BorderStyle(BorderStyleBuilder builder) {
        this.verticalStyle = builder.verticalStyle;
        this.horizontalStyle = builder.horizontalStyle;
        this.edgeStyle = builder.edgeStyle;
        this.cornerChar = builder.cornerChar;
        this.verticalChar = builder.verticalChar;
        this.horizontalChar = builder.horizontalChar;
        this.modifiedChar = builder.modifiedChar;
    }

    public static BorderStyleBuilder builder(){
        return new BorderStyleBuilder();
    }

    /**
     * @deprecated As of 3.1.3, use {@link BorderStyle#builder()} instead. This will be removed in a future release.
     * */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static BorderStyleBuilder immutableBuilder() {
        return builder();
    }

    public StyleBuilder styleBuilder() {
        return Clique.styleBuilder();
    }

    public boolean hasModifiedChar(){
        return modifiedChar;
    }

    public static BorderStyle fromSpec(BorderSpec spec){
        return switch (spec){
            case BorderStyle b -> b;
            default -> BorderStyle.builder().uniformStyle(spec.style()).build();
        };
    }


    public String style() {
        return "";
    }

    /** @deprecated Use {@link #getHorizontalStyle()} instead */
    @Deprecated(since = "3.1")
    public AnsiCode[] getHorizontalBorderStyles() {
        return this.getHorizontalStyle();
    }

    public AnsiCode[] getHorizontalStyle() {
        return this.horizontalStyle.clone();
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
        return this.edgeStyle.clone();
    }


    /** @deprecated Use {@link #getVerticalStyle()} instead */
    @Deprecated(since = "3.1")
    public AnsiCode[] getVerticalBorderStyles() {
        return this.getVerticalStyle();
    }

    public AnsiCode[] getVerticalStyle() {
        return this.verticalStyle.clone();
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BorderStyle that = (BorderStyle) object;
        return cornerChar == that.cornerChar && verticalChar == that.verticalChar && horizontalChar == that.horizontalChar && Arrays.equals(verticalStyle, that.verticalStyle) && Arrays.equals(horizontalStyle, that.horizontalStyle) && Arrays.equals(edgeStyle, that.edgeStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cornerChar, verticalChar, horizontalChar, Arrays.hashCode(verticalStyle), Arrays.hashCode(horizontalStyle), Arrays.hashCode(edgeStyle));

    }

    public static class BorderStyleBuilder {
        private final static String NULL_ANSI_CODE_WARNING = "Ansi codes cannot be null";
        private final static AnsiCode[] NONE = new AnsiCode[0];

        private AnsiCode[] verticalStyle = NONE;
        private AnsiCode[] horizontalStyle = NONE;
        private AnsiCode[] edgeStyle = NONE;
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
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.verticalStyle = styles;
            return this;
        }

        public BorderStyleBuilder verticalStyle(String style) {
            Objects.requireNonNull(style, "Vertical style cannot be null");
            return this.verticalStyle(getAnsiCodes(style));
        }

        /** @deprecated Use {@link #horizontalStyle(AnsiCode...)} instead */
        @Deprecated(since = "3.1")
        public BorderStyleBuilder horizontalBorderStyles(AnsiCode... styles) {
            return this.horizontalStyle(styles);
        }

        public BorderStyleBuilder horizontalStyle(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.horizontalStyle = styles;
            return this;
        }

        public BorderStyleBuilder horizontalStyle(String style) {
            Objects.requireNonNull(style, "Horizontal style cannot be null");
            return this.horizontalStyle(getAnsiCodes(style));
        }

        /** @deprecated Use {@link #cornerStyle(AnsiCode...)} instead */
        @Deprecated
        public BorderStyleBuilder edgeBorderStyles(AnsiCode... styles) {
            return this.cornerStyle(styles);
        }

        public BorderStyleBuilder cornerStyle(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.edgeStyle = styles;
            return this;
        }

        public BorderStyleBuilder cornerStyle(String style) {
            Objects.requireNonNull(style, "Corner style cannot be null");
            return this.cornerStyle(getAnsiCodes(style));
        }

        public BorderStyleBuilder uniformStyle(AnsiCode... styles) {
            this.cornerStyle(styles);
            this.horizontalStyle(styles);
            return this.verticalStyle(styles);
        }

        public BorderStyleBuilder uniformStyle(String style) {
            Objects.requireNonNull(style, "Uniform style cannot be null");
            return this.uniformStyle(getAnsiCodes(style));
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

        AnsiCode[] getAnsiCodes(String styles){
            return ParserUtils.getAnsiCodes(styles).toArray(AnsiCode[]::new);  //Uses default delimiter
        }

        public BorderStyle build() {
            return new BorderStyle(this);
        }
    }
}