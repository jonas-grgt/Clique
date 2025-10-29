package core.clique;

import core.style.StyleBuilder;
import core.style.StyleBuilderImpl;
import parser.token.stringparser.DynamicAnsiStringParser;
import parser.token.stringparser.interfaces.AnsiStringParser;

import static parser.token.stringparser.AnsiParserType.DEFAULT;
import static parser.token.stringparser.AnsiParserType.DYNAMIC;

/**
 * A facade class to hide the instantiation of multiple implementations of different classes
 * */
public final class Clique {
    public static StyleBuilder styleBuilder(){
        return new StyleBuilderImpl();
    }

    public static AnsiStringParser ofDefault(){
        return AnsiStringParser.typeOf(DEFAULT);
    }

    public static DynamicAnsiStringParser ofDynamic(){
        return (DynamicAnsiStringParser) AnsiStringParser.typeOf(DYNAMIC);
    }
}
