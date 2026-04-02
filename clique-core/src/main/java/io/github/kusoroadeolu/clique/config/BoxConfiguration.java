package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.boxes.AbstractBox;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.MiscUtils.assertStyleNotNull;

/**
 * A class for styling table borders
 * @since 1.1.0
 * */
@Stable(since = "3.2.0")
public class BoxConfiguration {
    public static final BoxConfiguration DEFAULT = new BoxConfiguration();

    private final int centerPadding;
    private final TextAlign textAlign;
    private final AnsiStringParser parser;
    private final BorderStyle borderStyle;
    private final boolean autoSize;
    private final int padding;

    private BoxConfiguration() {
        this(new BoxConfigurationBuilder());
    }

    private BoxConfiguration(BoxConfigurationBuilder builder) {
        this.centerPadding = builder.centerPadding;
        this.textAlign = builder.textAlign;
        this.parser = builder.parser;
        this.borderStyle = builder.borderStyle;
        this.autoSize = builder.autoSize;
        this.padding = builder.padding;
    }

    public static BoxConfigurationBuilder builder() {
        return new BoxConfigurationBuilder();
    }

    /**
     * @deprecated As of 3.1.3, use {@link BoxConfiguration#builder()} instead. This will be removed in a future release.
     * */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static BoxConfigurationBuilder immutableBuilder() {
        return builder();
    }

    @InternalApi(since = "3.1.3")
    public static BoxConfiguration fromBorderStyle(BorderSpec style) {
        assertStyleNotNull(style);
        return BoxConfiguration
                .builder()
                .borderStyle(style)
                .build();
    }

    public int getPadding() {
        return this.padding;
    }

    /**
     * @deprecated As of 3.1.0, due to confusing/incorrect semantics. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public int getCenterPadding() {
        return this.centerPadding;
    }

    /**
     * @deprecated As of 3.1.3, in favor of {@link AbstractBox.BoxDimensionBuilder#autosize()}. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1.3")
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

    @Override
    public String toString() {
        return "BoxConfiguration[" +
                "textAlign=" + textAlign +
                ", parser=" + parser +
                ", borderStyle=" + borderStyle +
                ", autoSize=" + autoSize +
                ", padding=" + padding +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BoxConfiguration that = (BoxConfiguration) object;
        return centerPadding == that.centerPadding && autoSize == that.autoSize && textAlign == that.textAlign && parser.equals(that.parser) && Objects.equals(borderStyle, that.borderStyle) && padding == that.padding;
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, autoSize, textAlign, parser, borderStyle);
    }

    public static class BoxConfigurationBuilder {
        private int centerPadding = 2;
        private TextAlign textAlign = TextAlign.CENTER;
        private AnsiStringParser parser = AnsiStringParser.DEFAULT;
        private BorderStyle borderStyle = null;
        private boolean autoSize = false;
        private int padding = 2;

        /**
         * @deprecated As of 3.1, due to confusing/incorrect semantics. This will be removed in a future release.
         */
        @Deprecated(since = "3.1")
        public BoxConfigurationBuilder centerPadding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Center padding cannot be negative");
            this.centerPadding = padding;
            return this;
        }

        public BoxConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }

        /**
         * @deprecated As of 3.1.3, in favor of {@link AbstractBox.BoxDimensionBuilder#autosize()}. This will be removed in a future release.
         */
        @Deprecated(since = "3.1.3", forRemoval = true)
        public BoxConfigurationBuilder autoSize() {
            this.autoSize = true;
            return this;
        }

        public BoxConfigurationBuilder borderStyle(BorderSpec spec) {
            Objects.requireNonNull(spec, "Border style cannot be null");
            this.borderStyle = BorderStyle.fromSpec(spec);
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