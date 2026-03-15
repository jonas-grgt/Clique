package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

public class FrameConfiguration {
    public static final FrameConfiguration DEFAULT = new FrameConfiguration();

    private final int centerPadding;
    private final FrameAlign align;
    private final AnsiStringParser parser;
    private final BorderStyle borderStyle;
    private final boolean autoSize;

    private FrameConfiguration() {
        this(new FrameConfigurationBuilder());
    }

    private FrameConfiguration(FrameConfigurationBuilder builder) {
        this.centerPadding = builder.centerPadding;
        this.align = builder.frameAlign;
        this.parser = builder.parser;
        this.borderStyle = builder.borderStyle;
        this.autoSize = builder.autoSize;
    }

    public static FrameConfigurationBuilder immutableBuilder() {
        return new FrameConfigurationBuilder();
    }

    public int getCenterPadding() {
        return this.centerPadding;
    }

    public boolean getAutoSize() {
        return this.autoSize;
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
                "centerPadding=" + centerPadding +
                ", frameAlign=" + align +
                ", parser=" + parser +
                ", borderStyle=" + borderStyle +
                ", autoSize=" + autoSize +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        FrameConfiguration that = (FrameConfiguration) object;
        return centerPadding == that.centerPadding && autoSize == that.autoSize && align == that.align && parser.equals(that.parser) && Objects.equals(borderStyle, that.borderStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPadding, autoSize, align, parser, borderStyle);
    }

    public static class FrameConfigurationBuilder {
        private int centerPadding = 2;
        private FrameAlign frameAlign = FrameAlign.CENTER;
        private AnsiStringParser parser = Clique.parser();
        private BorderStyle borderStyle = null;
        private boolean autoSize = false;

        public FrameConfigurationBuilder centerPadding(int centerPadding) {
            if (centerPadding > 0) this.centerPadding = centerPadding;
            return this;
        }

        public FrameConfigurationBuilder autoSize() {
            this.autoSize = true;
            return this;
        }

        public FrameConfigurationBuilder borderStyle(BorderStyle borderStyle) {
            Objects.requireNonNull(borderStyle, "Border style cannot be null");
            this.borderStyle = borderStyle;
            return this;
        }

        public FrameConfigurationBuilder frameAlign(FrameAlign align) {
            Objects.requireNonNull(frameAlign, "DefaultFrame align cannot be null");
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