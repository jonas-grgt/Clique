package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.Box;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.frame.Frame;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.progressbar.IterableProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
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

    public static TableHeaderBuilder table(String borderColor) {
        return table(TableConfiguration.builder().borderColor(borderColor).build());
    }

    public static TableHeaderBuilder table(TableType type, String borderColor) {
        return table(type, TableConfiguration.builder().borderColor(borderColor).build());
    }

    public static TableHeaderBuilder table(AnsiCode... borderColor) {
        return table(TableConfiguration.builder().borderColor(borderColor).build());
    }

    public static TableHeaderBuilder table(TableType type, AnsiCode... borderColor) {
        return table(type, TableConfiguration.builder().borderColor(borderColor).build());
    }

    // BOX
    public static Box box() {
        return new Box();
    }

    public static Box box(BoxConfiguration configuration) {
        return new Box(configuration);
    }

    public static Box box(BoxType type, BoxConfiguration configuration) {
        return new Box(type, configuration);
    }

    public static Box box(BoxType type) {
        return new Box(type);
    }

    public static Box box(AnsiCode... borderColor) {
        return new Box(BoxType.ROUNDED, BoxConfiguration.builder().borderColor(borderColor).build());
    }

    public static Box box(BoxType type, AnsiCode... borderColor) {
        return new Box(type, BoxConfiguration.builder().borderColor(borderColor).build());
    }

    public static Box box(String borderColor) {
        return new Box(BoxType.ROUNDED, BoxConfiguration.builder().borderColor(borderColor).build());
    }

    public static Box box(BoxType type, String borderColor) {
        return new Box(type, BoxConfiguration.builder().borderColor(borderColor).build());
    }


    // INDENTER
    public static Indenter indenter() {
        return new Indenter();
    }

    public static Indenter indenter(IndenterConfiguration indenterConfiguration) {
        return new Indenter(indenterConfiguration);
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
        return new Frame();
    }

    public static Frame frame(FrameConfiguration configuration) {
        return new Frame(configuration);
    }

    public static Frame frame(BoxType type) {
        return new Frame(type);
    }

    public static Frame frame(BoxType type, FrameConfiguration configuration) {
        return new Frame(configuration, type);
    }

    public static Frame frame(String borderColor) {
        return new Frame(FrameConfiguration.builder().borderColor(borderColor).build());
    }

    public static Frame frame(BoxType type, String borderColor) {
        return new Frame(FrameConfiguration.builder().borderColor(borderColor).build(), type);
    }

    public static Frame frame(AnsiCode... borderColor) {
        return new Frame(FrameConfiguration.builder().borderColor(borderColor).build());
    }

    public static Frame frame(BoxType type, AnsiCode... borderColor) {
        return new Frame(FrameConfiguration.builder().borderColor(borderColor).build(), type);
    }


    // TREE
    public static Tree tree(String label) {
        return new Tree(label);
    }

    public static Tree tree(String label, TreeConfiguration configuration) {
        return new Tree(label, configuration);
    }

    public static Tree tree(String label, String connectorColor) {
        return new Tree(label, TreeConfiguration.builder().connectorColor(connectorColor).build());
    }

    public static Tree tree(String label, AnsiCode... connectorColor) {
        return new Tree(label, TreeConfiguration.builder().connectorColor(connectorColor).build());
    }

}