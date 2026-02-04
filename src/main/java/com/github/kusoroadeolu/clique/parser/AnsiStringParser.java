package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.config.ParserConfiguration;

import java.io.PrintStream;
import java.util.Map;

public interface AnsiStringParser {

    String parse(String stringToParse);

    default void print(String string){
        System.out.println(this.parse(string));
    }

    default void print(Object object){
        System.out.println(this.parse(object.toString()));
    }

    String parse(Object object);

    String getOriginalString(String string);


}
