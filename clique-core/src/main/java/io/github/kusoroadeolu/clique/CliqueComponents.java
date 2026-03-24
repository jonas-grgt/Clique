package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.BoxFactory;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.frame.DefaultFrame;
import io.github.kusoroadeolu.clique.frame.Frame;
import io.github.kusoroadeolu.clique.indent.DefaultIndenter;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;
import io.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.TableFactory;
import io.github.kusoroadeolu.clique.tables.TableType;
import io.github.kusoroadeolu.clique.tree.Tree;

import static io.github.kusoroadeolu.clique.config.ProgressBarConfiguration.fromEasing;

/**
 * Sub-facade for all rendering components: boxes, tables, frames, trees, indenters, and progress bars.
 */
final class CliqueComponents {

    private CliqueComponents() {
        throw new AssertionError();
    }

    // TABLE

    public static TableHeaderBuilder table() {
        return TableFactory.getTableBuilder(TableType.BOX_DRAW);
    }

    public static TableHeaderBuilder table(TableConfiguration configuration) {
        return TableFactory.getTableBuilder(TableType.BOX_DRAW, configuration);
    }

    public static TableHeaderBuilder table(TableType type, TableConfiguration configuration) {
        return TableFactory.getTableBuilder(type, configuration);
    }

    public static TableHeaderBuilder table(TableType type) {
        return TableFactory.getTableBuilder(type);
    }

    public static TableHeaderBuilder table(BorderStyle style) {
        return table(TableConfiguration.fromBorderStyle(style));
    }

    public static TableHeaderBuilder table(TableType type, BorderStyle style) {
        return table(type, TableConfiguration.fromBorderStyle(style));
    }

    // BOX

    public static BoxDimensionBuilder box() {
        return BoxFactory.getBoxDimensionBuilder(BoxType.ROUNDED);
    }

    public static BoxDimensionBuilder box(BoxConfiguration configuration) {
        return BoxFactory.getBoxDimensionBuilder(BoxType.ROUNDED, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type, BoxConfiguration configuration) {
        return BoxFactory.getBoxDimensionBuilder(type, configuration);
    }

    public static BoxDimensionBuilder box(BoxType type) {
        return BoxFactory.getBoxDimensionBuilder(type);
    }

    public static BoxDimensionBuilder box(BorderStyle style) {
        return BoxFactory.getBoxDimensionBuilder(BoxType.ROUNDED, BoxConfiguration.fromBorderStyle(style));
    }

    public static BoxDimensionBuilder box(BoxType type, BorderStyle style) {
        return BoxFactory.getBoxDimensionBuilder(type, BoxConfiguration.fromBorderStyle(style));
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
        return new ProgressBar(total, preset.getConfiguration());
    }

    public static ProgressBar progressBar(int total, EasingConfiguration easing) {
        return new ProgressBar(total, ProgressBarConfiguration.fromEasing(easing));
    }


    // FRAME
    public static Frame frame() {
        return new DefaultFrame();
    }

    public static Frame frame(FrameConfiguration configuration) {
        return new DefaultFrame(configuration);
    }

    public static Frame frame(BoxType type) {
        return new DefaultFrame(type);
    }

    public static Frame frame(BoxType type, FrameConfiguration configuration) {
        return new DefaultFrame(configuration, type);
    }

    public static Frame frame(BorderStyle style) {
        return new DefaultFrame(FrameConfiguration.fromBorderStyle(style));
    }

    public static Frame frame(BoxType type, BorderStyle style) {
        return new DefaultFrame(FrameConfiguration.fromBorderStyle(style), type);
    }


    // TREE

    public static Tree tree(String label) {
        return new Tree(label);
    }

    public static Tree tree(String label, TreeConfiguration configuration) {
        return new Tree(label, configuration);
    }


    // DEPRECATED BOX METHODS
    /**
     * @deprecated As of 3.1, use {@link #table(TableType)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable(TableType type) {
        return TableFactory.getCustomizableTableBuilder(type);
    }

    /**
     * @deprecated As of 3.1, use {@link #table(TableType, TableConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable(TableType type, TableConfiguration configuration) {
        return TableFactory.getCustomizableTableBuilder(type, configuration);
    }

    /**
     * @deprecated As of 3.1, use {@link #table()} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable() {
        return customizableTable(TableConfiguration.DEFAULT);
    }

    /**
     * @deprecated As of 3.1, use {@link #table(TableConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable(TableConfiguration configuration) {
        return customizableTable(TableType.BOX_DRAW, configuration);
    }

        /**
         * @deprecated As of 3.1, use {@link #box(BoxType, BoxConfiguration)} instead. This will be removed in a future release.
         */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type, BoxConfiguration configuration) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type, configuration);
    }

    /**
     * @deprecated As of 3.1, use {@link #box(BoxType)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type);
    }

    /**
     * @deprecated As of 3.1, use {@link #box()} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox() {
        return customizableBox(BoxType.ROUNDED);
    }

    /**
     * @deprecated As of 3.1, use {@link #box(BoxConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxConfiguration configuration) {
        return customizableBox(BoxType.ROUNDED, configuration);
    }
}