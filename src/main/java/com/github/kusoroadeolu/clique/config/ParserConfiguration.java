package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;

import java.util.Objects;

public class ParserConfiguration {

    private final String delimiter;
    private final boolean enableStrictParsing;
    private final boolean enableAutoCloseTags;
    public final static ParserConfiguration DEFAULT = new ParserConfiguration();

    @Deprecated(since = "1.2.1", forRemoval = true)
    public static ParserConfiguration builder(){
        return new ParserConfiguration();
    }

    //Default Configuration
    private ParserConfiguration(){
        this.delimiter = String.valueOf(',');
        this.enableStrictParsing = false;
        this.enableAutoCloseTags = false;
    }

    private ParserConfiguration(ParserConfigurationBuilder builder) {
        this.delimiter = builder.delimiter;
        this.enableStrictParsing = builder.enableStrictParsing;
        this.enableAutoCloseTags = builder.enableAutoCloseTags;
    }

    public static ParserConfigurationBuilder immutableBuilder() {
        return new ParserConfigurationBuilder();
    }

    public boolean getEnableStrictParsing() {
        return enableStrictParsing;
    }

    public boolean getEnableAutoCloseTags() {
        return enableAutoCloseTags;
    }

    public String getDelimiter() {
        return delimiter;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        ParserConfiguration that = (ParserConfiguration) object;
        return enableStrictParsing == that.enableStrictParsing && enableAutoCloseTags == that.enableAutoCloseTags && delimiter.equals(that.delimiter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enableAutoCloseTags, enableStrictParsing, delimiter);
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public ParserConfiguration enableAutoCloseTags() {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public ParserConfiguration enableStrictParsing() {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public ParserConfiguration delimiter(char delimiter) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public static class ParserConfigurationBuilder {
        private String delimiter = String.valueOf(',');
        private boolean enableStrictParsing = false;
        private boolean enableAutoCloseTags = false;

        public ParserConfigurationBuilder enableAutoCloseTags() {
            this.enableAutoCloseTags = true;
            return this;
        }

        public ParserConfigurationBuilder enableStrictParsing() {
            this.enableStrictParsing = true;
            return this;
        }

        public ParserConfigurationBuilder delimiter(char delimiter) {
            this.delimiter = String.valueOf(delimiter);
            return this;
        }

        public ParserConfiguration build() {
            return new ParserConfiguration(this);
        }
    }
}