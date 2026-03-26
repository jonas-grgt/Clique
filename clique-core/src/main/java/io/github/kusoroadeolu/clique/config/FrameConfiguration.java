package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.MiscUtils.assertStyleNotNull;

public class FrameConfiguration {
    public static final FrameConfiguration DEFAULT = new FrameConfiguration();

    private final int padding;
    private final FrameAlign align;
    private final AnsiStringParser parser;
    private final BorderStyle borderStyle;

    private FrameConfiguration() {
        this(new FrameConfigurationBuilder());
    }

    private FrameConfiguration(FrameConfigurationBuilder builder) {
        this.padding = builder.padding;
        this.align = builder.frameAlign;
        this.parser = builder.parser;
        this.borderStyle = builder.borderStyle;
    }

    public static FrameConfigurationBuilder builder() {
        return new FrameConfigurationBuilder();
    }

    /**
     * @deprecated As of 3.1.3, use {@link FrameConfiguration#builder()} instead. This will be removed in a future release.
     * */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static FrameConfigurationBuilder immutableBuilder() {
        return builder();
    }

    public static FrameConfiguration fromBorderStyle(BorderStyle style) {
        assertStyleNotNull(style);
        return FrameConfiguration.immutableBuilder().borderStyle(style).build();
    }

    public int getPadding() {
        return this.padding;
    }

    public BorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    public FrameAlign getFrameAlign() {
        return this.align;
    }

    public AnsiStringParser getParser() {
        return this.parser;
    }

    public String toString() {
        return "FrameConfiguration[" +
                "centerPadding=" + padding +
                ", frameAlign=" + align +
                ", parser=" + parser +
                ", borderStyle=" + borderStyle +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        FrameConfiguration that = (FrameConfiguration) object;
        return padding == that.padding && align == that.align && parser.equals(that.parser) && Objects.equals(borderStyle, that.borderStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, align, parser, borderStyle);
    }

    public static class FrameConfigurationBuilder {
        private int padding = 2;
        private FrameAlign frameAlign = FrameAlign.CENTER;
        private AnsiStringParser parser = Clique.parser();
        private BorderStyle borderStyle = null;

        public FrameConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }

        public FrameConfigurationBuilder borderStyle(BorderStyle borderStyle) {
            Objects.requireNonNull(borderStyle, "Border style cannot be null");
            this.borderStyle = borderStyle;
            return this;
        }

        public FrameConfigurationBuilder frameAlign(FrameAlign align) {
            Objects.requireNonNull(align, "DefaultFrame align cannot be null");
            this.frameAlign = align;
            return this;
        }

        public FrameConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public FrameConfiguration build() {
            return new FrameConfiguration(this);
        }
    }
}