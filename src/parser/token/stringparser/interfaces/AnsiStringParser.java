package parser.token.stringparser.interfaces;

import parser.configuration.ParserConfiguration;

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
