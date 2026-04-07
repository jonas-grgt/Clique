package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.core.parser.ParserUtils;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @since 3.1.0
 */
@Stable(since = "3.2.0")
public class FrameConfiguration {
    public static final FrameConfiguration DEFAULT = new FrameConfiguration();

    private final int padding;
    private final FrameAlign align;
    private final AnsiStringParser parser;
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

    /**
     * @deprecated As of 3.1.3, use {@link FrameConfiguration#builder()} instead. This will be removed in a future release.
     */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static FrameConfigurationBuilder immutableBuilder() {
        return builder();
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

    public AnsiStringParser getParser() {
        return this.parser;
    }

    @Override
    public String toString() {
        return "FrameConfiguration[" +
                "padding=" + padding +
                ", frameAlign=" + align +
                ", parser=" + parser +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        FrameConfiguration that = (FrameConfiguration) object;
        return padding == that.padding && align == that.align && parser.equals(that.parser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, align, parser, Arrays.hashCode(borderColor));
    }

    public static class FrameConfigurationBuilder {
        private int padding = 2;
        private FrameAlign frameAlign = FrameAlign.CENTER;
        private AnsiStringParser parser = Clique.parser();
        private AnsiCode[] borderColor = new AnsiCode[0];

        public FrameConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }

        public FrameConfigurationBuilder borderColor(String borderColor) {
            return borderColor(ParserUtils.getAnsiCodes(borderColor, parser).toArray(AnsiCode[]::new));
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

        public FrameConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public FrameConfiguration build() {
            return new FrameConfiguration(this);
        }
    }
}