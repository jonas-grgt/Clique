package com.github.kusoroadeolu.clique;

import com.github.kusoroadeolu.clique.boxes.Box;
import com.github.kusoroadeolu.clique.boxes.BoxFactory;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.boxes.CustomizableBox;
import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.config.IndenterConfiguration;
import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.core.utils.AnsiDetector;
import com.github.kusoroadeolu.clique.indent.Indenter;
import com.github.kusoroadeolu.clique.indent.IndenterImpl;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;
import com.github.kusoroadeolu.clique.parser.AnsiStringParserImpl;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.style.StyleBuilderImpl;
import com.github.kusoroadeolu.clique.tables.CustomizableTable;
import com.github.kusoroadeolu.clique.tables.Table;
import com.github.kusoroadeolu.clique.tables.TableFactory;
import com.github.kusoroadeolu.clique.tables.TableType;

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

    public static Indenter indenter(IndenterConfiguration indenterConfiguration){
        return new IndenterImpl(indenterConfiguration);
    }

    public static void enableCliqueColors(boolean enable){
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }

}
