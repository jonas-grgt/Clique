package core.clique;

import core.style.StyleBuilder;
import core.style.StyleBuilderImpl;
import parser.token.stringparser.AnsiStringParserImpl;
import parser.token.stringparser.interfaces.AnsiStringParser;
import tables.Table;
import tables.configuration.TableConfiguration;
import tables.factory.TableFactory;
import tables.factory.TableType;

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

    public static Table table(TableType type){
        return TableFactory.getTable(type);
    }

    public static Table table(TableType type, TableConfiguration configuration){
        return TableFactory.getTable(type, configuration);
    }

}
