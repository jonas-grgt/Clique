package com.github.kusoroadeolu.clique;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.boxes.Box;
import com.github.kusoroadeolu.clique.boxes.BoxFactory;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.boxes.CustomizableBox;
import com.github.kusoroadeolu.clique.config.*;
import com.github.kusoroadeolu.clique.core.utils.AnsiDetector;
import com.github.kusoroadeolu.clique.indent.Indenter;
import com.github.kusoroadeolu.clique.indent.IndenterImpl;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;
import com.github.kusoroadeolu.clique.parser.AnsiStringParserImpl;
import com.github.kusoroadeolu.clique.parser.GlobalParserRegistry;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.style.StyleBuilderImpl;
import com.github.kusoroadeolu.clique.tables.CustomizableTable;
import com.github.kusoroadeolu.clique.tables.Table;
import com.github.kusoroadeolu.clique.tables.TableFactory;
import com.github.kusoroadeolu.clique.tables.TableType;
import com.github.kusoroadeolu.clique.themes.CliqueTheme;
import com.github.kusoroadeolu.clique.themes.CliqueThemes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static AnsiStringParser parser(ParserConfiguration configuration){
        return new AnsiStringParserImpl(configuration);
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

    public static ProgressBar progressBar(int total){
        return new ProgressBar(total);
    }

    public static ProgressBar progressBar(int total, ProgressBarConfiguration configuration){
        return new ProgressBar(total, configuration);
    }

    public static void enableCliqueColors(boolean enable){
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }

    public static void registerStyle(String style, AnsiCode code){
        GlobalParserRegistry.registerStyle(style, code);
    }

    public static void registerStyles(Map<String, AnsiCode> codes){
        GlobalParserRegistry.registerStyles(codes);
    }

    public static void registerTheme(String name) {
        CliqueThemes.register(name);
    }

    public static void registerThemes(String... themes){
        CliqueThemes.registerThemes(themes);
    }

    public static void registerThemes(Collection<String> themes){
        CliqueThemes.registerThemes(themes);
    }

    public static void registerAllThemes() {
        CliqueThemes.registerAll();
    }

    public static List<CliqueTheme> discoverThemes(){
        return CliqueThemes.discover();
    }

    public static Optional<CliqueTheme> findTheme(String name){
        return CliqueThemes.find(name);
    }



}
