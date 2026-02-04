package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.indent.Flag;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

public class IndenterConfiguration {

    private final AnsiStringParser parser;
    private final int indentLevel;
    private final String defaultFlag;
    public final static IndenterConfiguration DEFAULT = new IndenterConfiguration();


    private IndenterConfiguration() {
        this.indentLevel = 1;
        this.parser = Clique.parser(ParserConfiguration.immutableBuilder().enableAutoCloseTags().build());
        this.defaultFlag = " "; // default to a space
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
        private String defaultFlag = " ";

        public IndenterConfigurationBuilder indentLevel(int indentLevel) {
            this.indentLevel = indentLevel;
            return this;
        }

        public IndenterConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public IndenterConfigurationBuilder defaultFlag(String defaultFlag) {
            this.defaultFlag = defaultFlag;
            return this;
        }

        public IndenterConfigurationBuilder defaultFlag(Flag defaultFlag) {
            this.defaultFlag = defaultFlag.flag();
            return this;
        }

        public IndenterConfiguration build() {
            return new IndenterConfiguration(this);
        }
    }
}