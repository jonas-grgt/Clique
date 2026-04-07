package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.BoxFactory;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.frame.DefaultFrame;
import io.github.kusoroadeolu.clique.frame.Frame;
import io.github.kusoroadeolu.clique.indent.DefaultIndenter;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.progressbar.IterableProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.TableFactory;
import io.github.kusoroadeolu.clique.tables.TableType;
import io.github.kusoroadeolu.clique.tree.Tree;

import java.util.Collection;

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

    public static TableHeaderBuilder table(BorderSpec style) {
        var borderStyle = BorderStyle.fromSpec(style);
        return table(TableConfiguration.fromBorderStyle(borderStyle));
    }

    public static TableHeaderBuilder table(TableType type, BorderSpec style) {
        var borderStyle = BorderStyle.fromSpec(style);
        return table(type, TableConfiguration.fromBorderStyle(borderStyle));
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

    public static BoxDimensionBuilder box(BorderSpec style) {
        var borderStyle = BorderStyle.fromSpec(style);
        return BoxFactory.getBoxDimensionBuilder(BoxType.ROUNDED, BoxConfiguration.fromBorderStyle(borderStyle));
    }

    public static BoxDimensionBuilder box(BoxType type, BorderSpec style) {
        var borderStyle = BorderStyle.fromSpec(style);
        return BoxFactory.getBoxDimensionBuilder(type, BoxConfiguration.fromBorderStyle(borderStyle));
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

    public static <T>IterableProgressBar<T> progressBar(Collection<T> collection) {
        return new IterableProgressBar<>(collection);
    }

    public static <T>IterableProgressBar<T> progressBar(Collection<T> collection, ProgressBarConfiguration configuration) {
        return new IterableProgressBar<>(collection, configuration);
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

    public static Frame frame(BorderSpec style) {
        var borderStyle = BorderStyle.fromSpec(style);
        return new DefaultFrame(FrameConfiguration.fromBorderStyle(borderStyle));
    }

    public static Frame frame(BoxType type, BorderSpec style) {
        var borderStyle = BorderStyle.fromSpec(style);
        return new DefaultFrame(FrameConfiguration.fromBorderStyle(borderStyle), type);
    }


    // TREE
    public static Tree tree(String label) {
        return new Tree(label);
    }

    public static Tree tree(String label, TreeConfiguration configuration) {
        return new Tree(label, configuration);
    }

}