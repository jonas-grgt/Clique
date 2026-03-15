package io.github.kusoroadeolu.clique;


import io.github.kusoroadeolu.clique.boxes.AbstractBox;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.BoxFactory;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.core.utils.AnsiDetector;
import io.github.kusoroadeolu.clique.frames.Frame;
import io.github.kusoroadeolu.clique.frames.FrameBuilder;
import io.github.kusoroadeolu.clique.frames.FrameType;
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
import io.github.kusoroadeolu.clique.tables.AbstractTable;
import io.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.TableFactory;
import io.github.kusoroadeolu.clique.tables.TableType;
import io.github.kusoroadeolu.clique.themeloader.CliqueThemeLoader;

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

    public static StyleBuilder styleBuilder() {
        return new DefaultStyleBuilder();
    }

    public static AnsiStringParser parser() {
        return new AnsiStringParserImpl();
    }

    public static AnsiStringParser parser(ParserConfiguration configuration) {
        return new AnsiStringParserImpl(configuration);
    }

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

    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type, BoxConfiguration configuration) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type, configuration);
    }

    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type);
    }

    public static CustomizableBoxDimensionBuilder customizableBox() {
        return customizableBox(BoxType.DEFAULT);
    }

    public static CustomizableBoxDimensionBuilder customizableBox(BoxConfiguration configuration) {
        return customizableBox(BoxType.DEFAULT, configuration);
    }

    public static Indenter indenter() {
        return new DefaultIndenter();
    }

    public static Indenter indenter(IndenterConfiguration indenterConfiguration) {
        return new DefaultIndenter(indenterConfiguration);
    }

    public static ProgressBar progressBar(int total) {
        return new ProgressBar(total);
    }

    public static ProgressBar progressBar(int total, ProgressBarConfiguration configuration) {
        return new ProgressBar(total, configuration);
    }

    public static ProgressBar progressBar(int total, ProgressBarPreset preset) {
        return progressBar(total, preset.getConfiguration());
    }

    public static void enableCliqueColors(boolean enable) {
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }

    public static void enableCliqueColors() {
        enableCliqueColors(true);
    }

    public static FrameBuilder frame(){
        return Frame.builder();
    }

    public static FrameBuilder frame(FrameConfiguration configuration){
        return Frame.builder(configuration);
    }

    public static FrameBuilder frame(FrameType type){
        return Frame.builder(type);
    }

    public static FrameBuilder frame(FrameType type, FrameConfiguration configuration){
        return Frame.builder(configuration, type);
    }

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

}

// https://patorjk.com/software/taag/