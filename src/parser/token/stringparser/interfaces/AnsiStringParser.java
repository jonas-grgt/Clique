package parser.token.stringparser.interfaces;

import parser.token.stringparser.AnsiParserType;
import parser.token.stringparser.DefaultAnsiStringParser;
import parser.token.stringparser.DynamicAnsiStringParser;

public interface AnsiStringParser {

    String parse(String stringToParse);

    default void print(String stringToParse){
        System.out.println(this.parse(stringToParse));
    }

    static AnsiStringParser typeOf(AnsiParserType type){
        if (type == null) throw new IllegalArgumentException("Parser type cannot be null");

        return switch (type){
            case DEFAULT -> new DefaultAnsiStringParser();
            case DYNAMIC -> new DynamicAnsiStringParser();
        };
    }
}
