package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.core.parser.ParserUtils;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * A class for styling table borders
 * @since 1.1.0
 * */
@Stable(since = "3.2.0")
public class BoxConfiguration {
    public static final BoxConfiguration DEFAULT = new BoxConfiguration();

    private final TextAlign textAlign;
    private final AnsiStringParser parser;
    private final AnsiCode[] borderColor;
    private final int padding;

    private BoxConfiguration() {
        this(new BoxConfigurationBuilder());
    }

    private BoxConfiguration(BoxConfigurationBuilder builder) {
        this.textAlign = builder.textAlign;
        this.parser = builder.parser;
        this.borderColor = builder.borderColor;
        this.padding = builder.padding;
    }

    public static BoxConfigurationBuilder builder() {
        return new BoxConfigurationBuilder();
    }



    public int getPadding() {
        return this.padding;
    }

    public AnsiCode[] getBorderColor() {
        return this.borderColor;
    }

    public TextAlign getTextAlign() {
        return this.textAlign;
    }

    public AnsiStringParser getParser() {
        return this.parser;
    }

    @Override
    public String toString() {
        return "BoxConfiguration[" +
                "textAlign=" + textAlign +
                ", parser=" + parser +
                ", borderColor=" + Arrays.toString(borderColor) +
                ", padding=" + padding +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BoxConfiguration that = (BoxConfiguration) object;
        return textAlign == that.textAlign && parser.equals(that.parser) &&  padding == that.padding && Arrays.equals(borderColor, that.borderColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, textAlign, parser, Arrays.hashCode(borderColor));
    }

    public static class BoxConfigurationBuilder {
        private TextAlign textAlign = TextAlign.CENTER;
        private AnsiStringParser parser = AnsiStringParser.DEFAULT;
        private AnsiCode[] borderColor = {};
        private int padding = 2;

        public BoxConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }

        public BoxConfigurationBuilder borderColor(String borderColor) {
            return borderColor(ParserUtils.getAnsiCodes(borderColor, parser));
        }

        public BoxConfigurationBuilder borderColor(AnsiCode... borderColor) {
            Objects.requireNonNull(borderColor, "Border color cannot be null");
            this.borderColor = borderColor;
            return this;
        }

        public BoxConfigurationBuilder textAlign(TextAlign textAlign) {
            Objects.requireNonNull(textAlign, "Text align cannot be null");
            this.textAlign = textAlign;
            return this;
        }

        public BoxConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public BoxConfiguration build() {
            return new BoxConfiguration(this);
        }
    }
}