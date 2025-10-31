package parser.token.stringparser.interfaces;

import parser.configuration.ParserConfiguration;

public interface AnsiStringParser {

    AnsiStringParser configuration(ParserConfiguration configuration);

    String parse(String stringToParse);

    default void print(String stringToParse){
        System.out.println(this.parse(stringToParse));
    }

    String getOriginalString();
}
