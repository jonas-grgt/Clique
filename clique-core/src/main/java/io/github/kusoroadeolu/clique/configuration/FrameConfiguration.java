package io.github.kusoroadeolu.clique.configuration;

import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.markup.ParserUtils;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @since 3.1.0
 */
@Stable(since = "3.2.0")
public final class FrameConfiguration {
    public static final FrameConfiguration DEFAULT = new FrameConfiguration();

    private final int padding;
    private final FrameAlign align;
    private final MarkupParser parser;
    private final AnsiCode[] borderColor;

    private FrameConfiguration() {
        this(new FrameConfigurationBuilder());
    }

    private FrameConfiguration(FrameConfigurationBuilder builder) {
        this.padding = builder.padding;
        this.align = builder.frameAlign;
        this.parser = builder.parser;
        this.borderColor = builder.borderColor;
    }

    public static FrameConfigurationBuilder builder() {
        return new FrameConfigurationBuilder();
    }

    public int getPadding() {
        return this.padding;
    }

    public AnsiCode[] getBorderColor() {
        return this.borderColor;
    }

    public FrameAlign getFrameAlign() {
        return this.align;
    }

    public MarkupParser getParser() {
        return this.parser;
    }

    @Override
    public String toString() {
        return "FrameConfiguration[" +
                "padding=" + padding +
                ", align=" + align +
                ", parser=" + parser +
                ", borderColor=" + Arrays.toString(borderColor) +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        FrameConfiguration that = (FrameConfiguration) object;
        return padding == that.padding && align == that.align && parser.equals(that.parser) && Arrays.equals(borderColor, that.borderColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, align, parser, Arrays.hashCode(borderColor));
    }

    public static class FrameConfigurationBuilder {
        private int padding = 2;
        private FrameAlign frameAlign = FrameAlign.CENTER;
        private MarkupParser parser = MarkupParser.DEFAULT;
        private AnsiCode[] borderColor = {};

        public FrameConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }

        public FrameConfigurationBuilder borderColor(String borderColor) {
            return borderColor(ParserUtils.getAnsiCodes(borderColor, parser));
        }

        public FrameConfigurationBuilder borderColor(AnsiCode... borderColor) {
            Objects.requireNonNull(borderColor, "Border color cannot be null");
            this.borderColor = borderColor;
            return this;
        }

        public FrameConfigurationBuilder frameAlign(FrameAlign align) {
            Objects.requireNonNull(align, "Frame align cannot be null");
            this.frameAlign = align;
            return this;
        }

        public FrameConfigurationBuilder parser(MarkupParser parser) {
            this.parser = parser;
            return this;
        }

        public FrameConfiguration build() {
            return new FrameConfiguration(this);
        }
    }
}