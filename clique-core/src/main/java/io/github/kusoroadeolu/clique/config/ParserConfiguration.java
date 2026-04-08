package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.core.documentation.Stable;

import java.util.Objects;

/**
 * @since 1.0.0
 * */
@Stable(since = "3.2.0")
public class ParserConfiguration {
    public static final ParserConfiguration DEFAULT = new ParserConfiguration();

    private final String delimiter;
    private final boolean enableStrictParsing;
    private final boolean enableAutoCloseTags;


    //Default Configuration
    private ParserConfiguration() {
        this(new ParserConfigurationBuilder());
    }

    private ParserConfiguration(ParserConfigurationBuilder builder) {
        this.delimiter = builder.delimiter;
        this.enableStrictParsing = builder.enableStrictParsing;
        this.enableAutoCloseTags = builder.enableAutoCloseTags;
    }

    public static ParserConfigurationBuilder builder() {
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

    @Override
    public String toString() {
        return "ParserConfiguration[" +
                "delimiter='" + delimiter + '\'' +
                ", enableStrictParsing=" + enableStrictParsing +
                ", enableAutoCloseTags=" + enableAutoCloseTags +
                ']';
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