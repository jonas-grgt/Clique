package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.BoxFactory;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.frame.DefaultFrame;
import io.github.kusoroadeolu.clique.indent.DefaultIndenter;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;
import io.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.TableFactory;
import io.github.kusoroadeolu.clique.tables.TableType;
import io.github.kusoroadeolu.clique.tree.Tree;

/**
 * Sub-facade for all rendering components: boxes, tables, frames, trees, indenters, and progress bars.
 */
public final class CliqueComponents {

    private CliqueComponents() {
        throw new AssertionError();
    }

    // TABLE

    public static TableHeaderBuilder table() {
        return table(TableType.BOX_DRAW);
    }

    public static TableHeaderBuilder table(TableConfiguration configuration) {
        return table(TableType.BOX_DRAW, configuration);
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

    // BOX

    public static BoxDimensionBuilder box() {
        return box(BoxType.ROUNDED);
    }

    public static BoxDimensionBuilder box(BoxConfiguration configuration) {
        return box(BoxType.ROUNDED, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type, BoxConfiguration configuration) {
        return BoxFactory.getBoxDimensionBuilder(type, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type) {
        return BoxFactory.getBoxDimensionBuilder(type);
    }

    // INDENTER

    public static Indenter indenter() {
        return new DefaultIndenter();
    }

    public static Indenter indenter(IndenterConfiguration indenterConfiguration) {
        return new DefaultIndenter(indenterConfiguration);
    }

    // PROGRESS BAR

    public static ProgressBar progressBar(int total) {
        return new ProgressBar(total);
    }

    public static ProgressBar progressBar(int total, ProgressBarConfiguration configuration) {
        return new ProgressBar(total, configuration);
    }

    public static ProgressBar progressBar(int total, ProgressBarPreset preset) {
        return progressBar(total, preset.getConfiguration());
    }

    // FRAME

    public static DefaultFrame frame() {
        return new DefaultFrame();
    }

    public static DefaultFrame frame(FrameConfiguration configuration) {
        return new DefaultFrame(configuration);
    }

    public static DefaultFrame frame(BoxType type) {
        return new DefaultFrame(type);
    }

    public static DefaultFrame frame(BoxType type, FrameConfiguration configuration) {
        return new DefaultFrame(configuration, type);
    }

    // TREE

    public static Tree tree(String label) {
        return new Tree(label);
    }

    public static Tree tree(String label, TreeConfiguration configuration) {
        return new Tree(label, configuration);
    }

    // DEPRECATED BOX METHODS

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