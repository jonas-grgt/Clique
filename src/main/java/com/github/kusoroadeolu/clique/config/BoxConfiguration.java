package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

public class BoxConfiguration {
    private final int centerPadding;
    private final TextAlign textAlign;
    private final AnsiStringParser parser;
    private final BorderStyle borderStyle;
    private final boolean autoSize;

    @Deprecated(since = "1.2.1", forRemoval = true)
    public static BoxConfiguration builder(){
        return new BoxConfiguration();
    }

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

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BoxConfiguration centerPadding(int centerPadding) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public boolean getAutoSize() {
        return this.autoSize;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BoxConfiguration autoSize(boolean autoSize) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public BorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BoxConfiguration borderStyle(BorderStyle borderStyle) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public TextAlign getTextAlign() {
        return this.textAlign;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BoxConfiguration textAlign(TextAlign textAlign) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public AnsiStringParser getParser() {
        return this.parser;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public BoxConfiguration parser(AnsiStringParser parser) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
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