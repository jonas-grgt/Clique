package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

public class BoxConfiguration {
    private final int centerPadding;
    private final TextAlign textAlign;
    private final AnsiStringParser parser;
    private final BorderStyle borderStyle;
    private final boolean autoSize;
    public final static BoxConfiguration DEFAULT = new BoxConfiguration();

    private BoxConfiguration(){
        this.centerPadding = 2;
        this.textAlign = TextAlign.CENTER;
        this.parser = Clique.parser();
        this.borderStyle = null;
        this.autoSize = false;
    }

    private BoxConfiguration(BoxConfigurationBuilder builder) {
        this.centerPadding = builder.centerPadding;
        this.textAlign = builder.textAlign;
        this.parser = builder.parser;
        this.borderStyle = builder.borderStyle;
        this.autoSize = builder.autoSize;
    }

    public static BoxConfigurationBuilder immutableBuilder(){
        return new BoxConfigurationBuilder();
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
    public TextAlign getTextAlign() {
        return this.textAlign;
    }
    public AnsiStringParser getParser() {
        return this.parser;
    }

    public String toString() {
        return "BoxConfiguration[" +
                "centerPadding=" + centerPadding +
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
        return centerPadding == that.centerPadding && autoSize == that.autoSize && textAlign == that.textAlign && parser.equals(that.parser) && Objects.equals(borderStyle, that.borderStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPadding, autoSize, textAlign, parser, borderStyle);
    }

    public static class BoxConfigurationBuilder {
        private int centerPadding = 2;
        private TextAlign textAlign = TextAlign.CENTER;
        private AnsiStringParser parser = Clique.parser();
        private BorderStyle borderStyle = null;
        private boolean autoSize = false;

        public BoxConfigurationBuilder centerPadding(int centerPadding) {
            if(centerPadding < 0){
                centerPadding = 2;
            }
            this.centerPadding = centerPadding;
            return this;
        }

        public BoxConfigurationBuilder autoSize() {
            this.autoSize = true;
            return this;
        }

        public BoxConfigurationBuilder borderStyle(BorderStyle borderStyle) {
            this.borderStyle = borderStyle;
            return this;
        }

        public BoxConfigurationBuilder textAlign(TextAlign textAlign) {
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