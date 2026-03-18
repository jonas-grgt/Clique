package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

public class BoxConfiguration {
    public static final BoxConfiguration DEFAULT = new BoxConfiguration();

    private final int padding;
    private final TextAlign textAlign;
    private final AnsiStringParser parser;
    private final BorderStyle borderStyle;
    private final boolean autoSize;

    private BoxConfiguration() {
        this(new BoxConfigurationBuilder());
    }

    private BoxConfiguration(BoxConfigurationBuilder builder) {
        this.padding = builder.padding;
        this.textAlign = builder.textAlign;
        this.parser = builder.parser;
        this.borderStyle = builder.borderStyle;
        this.autoSize = builder.autoSize;
    }

    public static BoxConfigurationBuilder immutableBuilder() {
        return new BoxConfigurationBuilder();
    }

    public int getPadding() {
        return this.padding;
    }

    /**
     * @deprecated As of 3.1, due to confusing/incorrect semantics. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public int getCenterPadding() {
        return this.padding;
    }

    public boolean getAutoSize() {
        return this.autoSize;
    }

    public BorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    public TextAlign getTextAlign() {
        return this.textAlign;
    }

    public AnsiStringParser getParser() {
        return this.parser;
    }

    public String toString() {
        return "BoxConfiguration[" +
                "centerPadding=" + padding +
                ", textAlign=" + textAlign +
                ", parser=" + parser +
                ", borderStyle=" + borderStyle +
                ", autoSize=" + autoSize +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BoxConfiguration that = (BoxConfiguration) object;
        return padding == that.padding && autoSize == that.autoSize && textAlign == that.textAlign && parser.equals(that.parser) && Objects.equals(borderStyle, that.borderStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, autoSize, textAlign, parser, borderStyle);
    }

    public static class BoxConfigurationBuilder {
        private int padding = 2;
        private TextAlign textAlign = TextAlign.CENTER;
        private AnsiStringParser parser = Clique.parser();
        private BorderStyle borderStyle = null;
        private boolean autoSize = false;

        /**
         * @deprecated As of 3.1, due to confusing/incorrect semantics. This will be removed in a future release.
         */
        @Deprecated(forRemoval = true, since = "3.1")
        public BoxConfigurationBuilder centerPadding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }


        public BoxConfigurationBuilder autoSize() {
            this.autoSize = true;
            return this;
        }

        public BoxConfigurationBuilder borderStyle(BorderStyle borderStyle) {
            Objects.requireNonNull(borderStyle, "Border style cannot be null");
            this.borderStyle = borderStyle;
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