package com.github.kusoroadeolu.parser.configuration;

public class ParserConfiguration {

    private String delimiter;
    private boolean enableStrictParsing;
    private boolean enableAutoCloseTags;

    //Default Configuration
    private ParserConfiguration(){
        this.delimiter = String.valueOf(',');
        this.enableStrictParsing = false;
        this.enableAutoCloseTags = false;
    }

    public static ParserConfiguration builder(){
        return new ParserConfiguration();
    }

    public ParserConfiguration enableAutoCloseTags() {
        this.enableAutoCloseTags = true;
        return this;
    }

    public ParserConfiguration enableStrictParsing() {
        this.enableStrictParsing = true;
        return this;
    }

    public ParserConfiguration delimiter(char delimiter) {
        this.delimiter = String.valueOf(delimiter);
        return this;
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
}
