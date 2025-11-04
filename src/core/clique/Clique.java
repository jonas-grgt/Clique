package core.clique;

import core.style.StyleBuilder;
import core.style.StyleBuilderImpl;
import parser.token.stringparser.AnsiStringParserImpl;
import parser.token.stringparser.interfaces.AnsiStringParser;
import core.misc.interfaces.Customizable;
import tables.interfaces.CustomizableTable;
import tables.interfaces.Table;
import tables.configuration.TableConfiguration;
import tables.factory.TableFactory;
import tables.factory.TableType;
import core.utils.AnsiDetector;

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

    public static CustomizableTable customizableTable(TableType type){
        return TableFactory.getCustomizableTable(type);
    }

    public static CustomizableTable customizableTable(TableType type, TableConfiguration configuration){
        return TableFactory.getCustomizableTable(type, configuration);
    }

    public static void enableCliqueColors(boolean enable){
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }

}
