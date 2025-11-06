package com.github.kusoroadeolu.core.clique;

import com.github.kusoroadeolu.boxes.factory.BoxFactory;
import com.github.kusoroadeolu.boxes.configuration.BoxConfiguration;
import com.github.kusoroadeolu.boxes.factory.BoxType;
import com.github.kusoroadeolu.boxes.interfaces.Box;
import com.github.kusoroadeolu.boxes.interfaces.CustomizableBox;
import com.github.kusoroadeolu.core.indent.Indenter;
import com.github.kusoroadeolu.core.indent.IndenterImpl;
import com.github.kusoroadeolu.core.style.StyleBuilder;
import com.github.kusoroadeolu.core.style.StyleBuilderImpl;
import com.github.kusoroadeolu.parser.token.stringparser.AnsiStringParserImpl;
import com.github.kusoroadeolu.parser.token.stringparser.interfaces.AnsiStringParser;
import com.github.kusoroadeolu.tables.interfaces.CustomizableTable;
import com.github.kusoroadeolu.tables.interfaces.Table;
import com.github.kusoroadeolu.tables.configuration.TableConfiguration;
import com.github.kusoroadeolu.tables.factory.TableFactory;
import com.github.kusoroadeolu.tables.factory.TableType;
import com.github.kusoroadeolu.core.utils.AnsiDetector;

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

    public static Box box(BoxType type, BoxConfiguration configuration){
        return BoxFactory.getBox(type, configuration);
    }

    public static Box box(BoxType type){
        return BoxFactory.getBox(type);
    }

    public static CustomizableBox customizableBox(BoxType type, BoxConfiguration configuration){
        return BoxFactory.getCustomizableBox(type, configuration);
    }

    public static CustomizableBox customizableBox(BoxType type){
        return BoxFactory.getCustomizableBox(type);
    }

    public static Indenter indenter(){
        return new IndenterImpl();
    }

    public static void enableCliqueColors(boolean enable){
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }

}
