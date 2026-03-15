package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.indent.Flag;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.BLANK;

public class IndenterConfiguration {
    public static final IndenterConfiguration DEFAULT = new IndenterConfiguration();

    private final AnsiStringParser parser;
    private final int indentLevel;
    private final String defaultFlag;


    private IndenterConfiguration() {
        this(new IndenterConfigurationBuilder());
    }

    private IndenterConfiguration(IndenterConfigurationBuilder builder) {
        this.indentLevel = builder.indentLevel;
        this.parser = builder.parser;
        this.defaultFlag = builder.defaultFlag;
    }


    public static IndenterConfigurationBuilder immutableBuilder() {
        return new IndenterConfigurationBuilder();
    }

    public String toString() {
        return "IndenterConfiguration[" +
                "parser=" + parser +
                ", indentLevel=" + indentLevel +
                ", defaultFlag='" + defaultFlag + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        IndenterConfiguration that = (IndenterConfiguration) object;
        return indentLevel == that.indentLevel && parser.equals(that.parser) && defaultFlag.equals(that.defaultFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parser, indentLevel, defaultFlag);
    }

    public AnsiStringParser getParser() {
        return parser;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public static class IndenterConfigurationBuilder {
        private int indentLevel = 1;
        private AnsiStringParser parser = Clique.parser(ParserConfiguration.immutableBuilder().enableAutoCloseTags().build());
        private String defaultFlag = BLANK;

        public IndenterConfigurationBuilder indentLevel(int indentLevel) {
            if (indentLevel < 1) throw new IllegalArgumentException("Indent level cannot be less than 1");
            this.indentLevel = indentLevel;
            return this;
        }

        public IndenterConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public IndenterConfigurationBuilder defaultFlag(String defaultFlag) {
            Objects.requireNonNull(defaultFlag, "Default flag cannot be null");
            this.defaultFlag = defaultFlag;
            return this;
        }

        public IndenterConfigurationBuilder defaultFlag(Flag defaultFlag) {
            Objects.requireNonNull(defaultFlag, "Default flag cannot be null");
            this.defaultFlag = defaultFlag.flag();
            return this;
        }

        public IndenterConfiguration build() {
            return new IndenterConfiguration(this);
        }
    }
}