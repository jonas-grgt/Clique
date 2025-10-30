package core.clique;

import core.style.StyleBuilder;
import core.style.StyleBuilderImpl;
import parser.token.stringparser.AnsiStringParserImpl;
import parser.token.stringparser.interfaces.AnsiStringParser;

/**
 * A facade class to hide the instantiation of multiple implementations of different classes
 * */
public final class Clique {
    public static StyleBuilder styleBuilder(){
        return new StyleBuilderImpl();
    }

    public static AnsiStringParser parser(){
        return new AnsiStringParserImpl();
    }

}
