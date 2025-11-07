package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.indent.Flag;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

public class IndenterConfiguration {

    private AnsiStringParser parser;
    private int indentLevel;
    private String defaultFlag;

    public AnsiStringParser getParser() {
        return parser;
    }

    private IndenterConfiguration() {
        this.indentLevel = 1;
        this.parser = Clique.parser().configuration(
                ParserConfiguration.builder().enableAutoCloseTags()
        );
        this.defaultFlag = " "; // default to a space
    }

    public static IndenterConfiguration builder() {
        return new IndenterConfiguration();
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public IndenterConfiguration defaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
        return this;
    }

    public IndenterConfiguration defaultFlag(Flag defaultFlag) {
        return this.defaultFlag(defaultFlag.flag());
    }


    public IndenterConfiguration parser(AnsiStringParser parser) {
        this.parser = parser;
        return this;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public IndenterConfiguration indentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
        return this;
    }
}
