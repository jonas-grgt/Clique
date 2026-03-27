package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.parser.ParserUtils;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BorderColor implements BorderSpec{
    private final AnsiCode[] verticalStyle;
    private final AnsiCode[] horizontalStyle;
    private final AnsiCode[] cornerStyle;

    private BorderColor(BorderColorBuilder builder){
        verticalStyle = builder.verticalStyle;
        horizontalStyle = builder.horizontalStyle;
        cornerStyle = builder.cornerStyle;
    }

    public static BorderColorBuilder builder(){
        return new BorderColorBuilder();
    }

    public static BorderColor of(String style){
        return builder().uniformStyle(style).build();
    }

    public static BorderColor of(AnsiCode... codes){
        return builder().uniformStyle(codes).build();
    }

    public AnsiCode[] getHorizontalStyle(){
        return this.horizontalStyle.clone();
    }


    public AnsiCode[] getCornerStyle() {
        return this.cornerStyle.clone();
    }


    public AnsiCode[] getVerticalStyle() {
        return this.verticalStyle.clone();
    }

    @Override
    public AnsiCode[] styles() {
        List<AnsiCode> list = new ArrayList<>(Arrays.stream(verticalStyle).toList());
        list.addAll(Arrays.stream(horizontalStyle).toList());
        list.addAll(Arrays.stream(cornerStyle).toList());
        return list.toArray(AnsiCode[]::new);
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BorderColor that = (BorderColor) object;
        return Arrays.equals(verticalStyle, that.verticalStyle) && Arrays.equals(horizontalStyle, that.horizontalStyle) && Arrays.equals(cornerStyle, that.cornerStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(verticalStyle), Arrays.hashCode(horizontalStyle), Arrays.hashCode(cornerStyle));
    }

    @Override
    public String toString() {
        return "BorderColor[" +
                "verticalStyle=" + Arrays.toString(verticalStyle) +
                ", horizontalStyle=" + Arrays.toString(horizontalStyle) +
                ", cornerStyle=" + Arrays.toString(cornerStyle) +
                ']';
    }

    public static class BorderColorBuilder{
        private final static AnsiCode[] NONE = new AnsiCode[0];
        private final static String NULL_ANSI_CODE_WARNING = "Ansi codes cannot be null";

        private AnsiCode[] verticalStyle = NONE;
        private AnsiCode[] horizontalStyle = NONE;
        private AnsiCode[] cornerStyle = NONE;

        private BorderColorBuilder(){}

        public BorderColorBuilder verticalStyle(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.verticalStyle = styles;
            return this;
        }

        public BorderColorBuilder verticalStyle(String style) {
            Objects.requireNonNull(style, "Vertical style cannot be null");
            return this.verticalStyle(getAnsiCodes(style));
        }

        public BorderColorBuilder horizontalStyle(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.horizontalStyle = styles;
            return this;
        }

        public BorderColorBuilder horizontalStyle(String style) {
            Objects.requireNonNull(style, "Horizontal style cannot be null");
            return this.horizontalStyle(getAnsiCodes(style));
        }

        public BorderColorBuilder cornerStyle(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.cornerStyle = styles;
            return this;
        }

        public BorderColorBuilder cornerStyle(String style) {
            Objects.requireNonNull(style, "Corner style cannot be null");
            return this.cornerStyle(getAnsiCodes(style));
        }

        public BorderColorBuilder uniformStyle(AnsiCode... styles) {
            this.cornerStyle(styles);
            this.horizontalStyle(styles);
            return this.verticalStyle(styles);
        }

        public BorderColorBuilder uniformStyle(String style) {
            Objects.requireNonNull(style, "Uniform style cannot be null");
            return this.uniformStyle(getAnsiCodes(style));
        }

        AnsiCode[] getAnsiCodes(String styles){
            return ParserUtils.getAnsiCodes(styles).toArray(AnsiCode[]::new);  //Uses default delimiter
        }

        public BorderColor build(){
            return new BorderColor(this);
        }
    }
}
