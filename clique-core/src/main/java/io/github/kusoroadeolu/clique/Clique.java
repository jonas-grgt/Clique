package io.github.kusoroadeolu.clique;


import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.BoxFactory;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.core.utils.AnsiDetector;
import io.github.kusoroadeolu.clique.frame.DefaultFrame;
import io.github.kusoroadeolu.clique.indent.DefaultIndenter;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.parser.AnsiStringParserImpl;
import io.github.kusoroadeolu.clique.parser.GlobalStyleRegistry;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import io.github.kusoroadeolu.clique.style.DefaultStyleBuilder;
import io.github.kusoroadeolu.clique.style.StyleBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.TableFactory;
import io.github.kusoroadeolu.clique.tables.TableType;
import io.github.kusoroadeolu.clique.themeloader.CliqueThemeLoader;
import io.github.kusoroadeolu.clique.tree.Tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A facade class to hide the instantiation of multiple implementations of different classes
 *
 */
public final class Clique {

    //Change: Hide the default public constructor
    private Clique() {
        throw new AssertionError();
    }

    //STYLE BUILDER

    public static StyleBuilder styleBuilder() {
        return new DefaultStyleBuilder();
    }


    //PARSER

    public static AnsiStringParser parser() {
        return new AnsiStringParserImpl();
    }

    public static AnsiStringParser parser(ParserConfiguration configuration) {
        return new AnsiStringParserImpl(configuration);
    }

    //TABLE

    public static TableHeaderBuilder table() {
        return table(TableType.DEFAULT);
    }

    public static TableHeaderBuilder table(TableConfiguration configuration) {
        return table(TableType.DEFAULT, configuration);
    }

    public static TableHeaderBuilder table(TableType type) {
        return TableFactory.getTableBuilder(type);
    }

    public static TableHeaderBuilder table(TableType type, TableConfiguration configuration) {
        return TableFactory.getTableBuilder(type, configuration);
    }

    public static CustomizableTableHeaderBuilder customizableTable(TableType type) {
        return TableFactory.getCustomizableTableBuilder(type);
    }

    public static CustomizableTableHeaderBuilder customizableTable(TableType type, TableConfiguration configuration) {
        return TableFactory.getCustomizableTableBuilder(type, configuration);
    }

    public static CustomizableTableHeaderBuilder customizableTable() {
        return customizableTable(TableConfiguration.DEFAULT);
    }

    public static CustomizableTableHeaderBuilder customizableTable(TableConfiguration configuration) {
        return customizableTable(TableType.DEFAULT, configuration);
    }


    //BOX

    public static BoxDimensionBuilder box() {
        return box(BoxType.DEFAULT);
    }

    public static BoxDimensionBuilder box(BoxConfiguration configuration) {
        return box(BoxType.DEFAULT, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type, BoxConfiguration configuration) {
        return BoxFactory.getBoxDimensionBuilder(type, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type) {
        return BoxFactory.getBoxDimensionBuilder(type);
    }

    //INDENTER

    public static Indenter indenter() {
        return new DefaultIndenter();
    }

    public static Indenter indenter(IndenterConfiguration indenterConfiguration) {
        return new DefaultIndenter(indenterConfiguration);
    }


    //PROGRESS BAR

    public static ProgressBar progressBar(int total) {
        return new ProgressBar(total);
    }

    public static ProgressBar progressBar(int total, ProgressBarConfiguration configuration) {
        return new ProgressBar(total, configuration);
    }

    public static ProgressBar progressBar(int total, ProgressBarPreset preset) {
        return progressBar(total, preset.getConfiguration());
    }


    //FRAME

    public static DefaultFrame frame(){
        return new DefaultFrame();
    }

    public static DefaultFrame frame(FrameConfiguration configuration){
        return new DefaultFrame(configuration);
    }

    public static DefaultFrame frame(BoxType type){
        return new DefaultFrame(type);
    }

    public static DefaultFrame frame(BoxType type, FrameConfiguration configuration){
        return new DefaultFrame(configuration, type);
    }


    //TREE
    public static Tree tree(String label){
        return new Tree(label);
    }

    public static Tree tree(String label, TreeConfiguration configuration){
        return new Tree(label, configuration);
    }




    //CLIQUE CONFIG

    public static void enableCliqueColors(boolean enable) {
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }

    public static void enableCliqueColors() {
        enableCliqueColors(true);
    }



    // THEMES AND STYLE REGISTRATION

    public static void registerStyle(String style, AnsiCode code) {
        GlobalStyleRegistry.registerStyle(style, code);
    }

    public static void registerStyles(Map<String, AnsiCode> codes) {
        GlobalStyleRegistry.registerStyles(codes);
    }

    public static void registerTheme(String name) {
        CliqueThemeLoader.register(name);
    }

    public static void registerThemes(String... themes) {
        CliqueThemeLoader.registerThemes(themes);
    }

    public static void registerThemes(Collection<String> themes) {
        CliqueThemeLoader.registerThemes(themes);
    }

    public static void registerAllThemes() {
        CliqueThemeLoader.registerAll();
    }

    public static List<CliqueTheme> discoverThemes() {
        return CliqueThemeLoader.discover();
    }

    public static Optional<CliqueTheme> findTheme(String name) {
        return CliqueThemeLoader.find(name);
    }


    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type, BoxConfiguration configuration) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type, configuration);
    }

    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type);
    }

    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox() {
        return customizableBox(BoxType.DEFAULT);
    }

    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxConfiguration configuration) {
        return customizableBox(BoxType.DEFAULT, configuration);
    }


}

// https://patorjk.com/software/taag/