package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Arrays;
import java.util.Objects;

/**
 * A class for styling table borders
 *
 */
public class BorderStyle {
    public final static BorderStyle DEFAULT = new BorderStyle();

    private final AnsiCode[] verticalStyle;
    private final AnsiCode[] horizontalStyle;
    private final AnsiCode[] edgeStyle;
    private final StyleBuilder styleBuilder;

    private BorderStyle() {
        this(new BorderStyleBuilder());
    }

    private BorderStyle(BorderStyleBuilder builder) {
        this.verticalStyle = builder.verticalStyle;
        this.horizontalStyle = builder.horizontalStyle;
        this.edgeStyle = builder.edgeStyle;
        this.styleBuilder = Clique.styleBuilder();
    }

    public static BorderStyleBuilder immutableBuilder() {
        return new BorderStyleBuilder();
    }

    public StyleBuilder styleBuilder() {
        return this.styleBuilder;
    }

    public AnsiCode[] getHorizontalBorderStyles() {
        return this.horizontalStyle.clone();
    }


    public String toString() {
        return "BorderStyle[" +
                "verticalStyle=" + Arrays.toString(verticalStyle) +
                ", horizontalStyle=" + Arrays.toString(horizontalStyle) +
                ", edgeStyle=" + Arrays.toString(edgeStyle) +
                ", styleBuilder=" + styleBuilder +
                ']';
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BorderStyle that = (BorderStyle) object;
        return Arrays.equals(verticalStyle, that.verticalStyle) && Arrays.equals(horizontalStyle, that.horizontalStyle) && Arrays.equals(edgeStyle, that.edgeStyle) && styleBuilder.equals(that.styleBuilder);
    }

    public int hashCode() {
        return Objects.hash(Arrays.hashCode(verticalStyle), Arrays.hashCode(horizontalStyle), Arrays.hashCode(edgeStyle), styleBuilder);
    }


    public AnsiCode[] getEdgeBorderStyles() {
        return this.edgeStyle.clone();
    }


    public AnsiCode[] getVerticalBorderStyles() {
        return this.verticalStyle.clone();
    }

    public static class BorderStyleBuilder {
        private final static String NULL_ANSI_CODE_WARNING = "Ansi codes cannot be null";

        private AnsiCode[] verticalStyle;
        private AnsiCode[] horizontalStyle;
        private AnsiCode[] edgeStyle;

        public BorderStyleBuilder verticalBorderStyles(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.verticalStyle = styles;
            return this;
        }

        public BorderStyleBuilder horizontalBorderStyles(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.horizontalStyle = styles;
            return this;
        }

        public BorderStyleBuilder edgeBorderStyles(AnsiCode... styles) {
            Objects.requireNonNull(styles, NULL_ANSI_CODE_WARNING);
            this.edgeStyle = styles;
            return this;
        }

        public BorderStyle build() {
            return new BorderStyle(this);
        }
    }
}
