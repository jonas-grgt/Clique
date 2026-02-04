package com.github.kusoroadeolu.clique;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.boxes.*;
import com.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import com.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import com.github.kusoroadeolu.clique.config.*;
import com.github.kusoroadeolu.clique.core.utils.AnsiDetector;
import com.github.kusoroadeolu.clique.indent.Indenter;
import com.github.kusoroadeolu.clique.indent.IndenterImpl;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;
import com.github.kusoroadeolu.clique.parser.AnsiStringParserImpl;
import com.github.kusoroadeolu.clique.parser.GlobalParserRegistry;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;
import com.github.kusoroadeolu.clique.progressbar.ProgressBarStyle;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.style.StyleBuilderImpl;
import com.github.kusoroadeolu.clique.tables.*;
import com.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import com.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
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

    public static TableHeaderBuilder table(TableType type){
        return TableFactory.getTableBuilder(type);
    }

    public static TableHeaderBuilder table(TableType type, TableConfiguration configuration){
        return TableFactory.getTableBuilder(type, configuration);
    }

    public static CustomizableTableHeaderBuilder customizableTable(TableType type){
        return TableFactory.getCustomizableTableBuilder(type);
    }

    public static CustomizableTableHeaderBuilder customizableTable(TableType type, TableConfiguration configuration){
        return TableFactory.getCustomizableTableBuilder(type, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type, BoxConfiguration configuration){
        return BoxFactory.getBoxDimensionBuilder(type, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type){
        return BoxFactory.getBoxDimensionBuilder(type);
    }

    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type, BoxConfiguration configuration){
        return BoxFactory.getCustomizableBoxDimensionBuilder(type, configuration);
    }

    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type){
        return BoxFactory.getCustomizableBoxDimensionBuilder(type);
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

    public static ProgressBar progressBar(int total, ProgressBarStyle style){
       return progressBar(total, style.getConfiguration());
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
