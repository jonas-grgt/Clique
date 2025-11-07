package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.config.ParserConfiguration;

public interface AnsiStringParser {

    AnsiStringParser configuration(ParserConfiguration configuration);

    String parse(String stringToParse);

    default void print(String string){
        System.out.println(this.parse(string));
    }

    default void print(Object object){
        System.out.println(this.parse(object.toString()));
    }

    String parse(Object object);

    String getOriginalString();
}
