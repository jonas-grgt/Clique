package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.BoxFactory;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.*;
import io.github.kusoroadeolu.clique.core.utils.AnsiDetector;
import io.github.kusoroadeolu.clique.frame.Frame;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import io.github.kusoroadeolu.clique.spi.RGBAnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.TableType;
import io.github.kusoroadeolu.clique.tree.Tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Main facade that delegates to CliqueStyles and CliqueComponents.
 */
public final class Clique {

    private Clique() {
        throw new AssertionError();
    }

    // STYLE BUILDER
    public static StyleBuilder styleBuilder() { return CliqueStyles.styleBuilder(); }

    // PARSER
    public static AnsiStringParser parser() { return CliqueStyles.parser(); }
    public static AnsiStringParser parser(ParserConfiguration configuration) { return CliqueStyles.parser(configuration); }

    // TABLE
    public static TableHeaderBuilder table() { return CliqueComponents.table(); }
    public static TableHeaderBuilder table(TableConfiguration configuration) { return CliqueComponents.table(configuration); }
    public static TableHeaderBuilder table(TableType type) { return CliqueComponents.table(type); }
    public static TableHeaderBuilder table(TableType type, TableConfiguration configuration) { return CliqueComponents.table(type, configuration); }

    public static TableHeaderBuilder table(TableType type, BorderStyle style){return CliqueComponents.table(type, style);}

    public static TableHeaderBuilder table(BorderStyle style){return CliqueComponents.table(style);}


    // BOX
    public static BoxDimensionBuilder box() { return CliqueComponents.box(); }
    public static BoxDimensionBuilder box(BoxConfiguration configuration) { return CliqueComponents.box(configuration); }
    public static BoxDimensionBuilder box(BoxType type, BoxConfiguration configuration) { return CliqueComponents.box(type, configuration); }
    public static BoxDimensionBuilder box(BoxType type) { return CliqueComponents.box(type); }

    public static BoxDimensionBuilder box(BoxType type, BorderStyle style){ return CliqueComponents.box(type, style);}

    public static BoxDimensionBuilder box(BorderStyle style){return CliqueComponents.box(style);}

    // INDENTER
    public static Indenter indenter() { return CliqueComponents.indenter(); }
    public static Indenter indenter(IndenterConfiguration indenterConfiguration) { return CliqueComponents.indenter(indenterConfiguration); }

    // PROGRESS BAR
    public static ProgressBar progressBar(int total) { return CliqueComponents.progressBar(total); }
    public static ProgressBar progressBar(int total, ProgressBarConfiguration configuration) { return CliqueComponents.progressBar(total, configuration); }
    public static ProgressBar progressBar(int total, ProgressBarPreset preset) { return CliqueComponents.progressBar(total, preset); }

    public static ProgressBar progressBar(int total, EasingConfiguration easing){
        return CliqueComponents.progressBar(total, easing);
    }

    // FRAME
    public static Frame frame() { return CliqueComponents.frame(); }
    public static Frame frame(FrameConfiguration configuration) { return CliqueComponents.frame(configuration); }
    public static Frame frame(BoxType type) { return CliqueComponents.frame(type); }
    public static Frame frame(BoxType type, FrameConfiguration configuration) { return CliqueComponents.frame(type, configuration); }

    public static Frame frame(BoxType type, BorderStyle style){return CliqueComponents.frame(type, style);}

    public static Frame frame(BorderStyle style){return CliqueComponents.frame(style);}

    // TREE
    public static Tree tree(String label) { return CliqueComponents.tree(label); }
    public static Tree tree(String label, TreeConfiguration configuration) { return CliqueComponents.tree(label, configuration); }

    // CLIQUE CONFIG
    public static void enableCliqueColors(boolean enable) {
        if (enable) AnsiDetector.enableCliqueColors();
        else AnsiDetector.disableCliqueColors();
    }
    public static void enableCliqueColors() { enableCliqueColors(true); }

    // RGB
    public static RGBAnsiCode rgb(int r, int g, int b) { return CliqueStyles.rgb(r, g, b); }
    public static RGBAnsiCode rgb(int r, int g, int b, boolean background) { return CliqueStyles.rgb(r, g, b, background); }

    // THEMES AND STYLE REGISTRATION
    public static void registerStyle(String style, AnsiCode code) { CliqueStyles.registerStyle(style, code); }
    public static void registerStyles(Map<String, AnsiCode> codes) { CliqueStyles.registerStyles(codes); }
    public static void registerTheme(String name) { CliqueStyles.registerTheme(name); }
    public static void registerThemes(String... names) { CliqueStyles.registerThemes(names); }
    public static void registerThemes(Collection<String> themes) { CliqueStyles.registerThemes(themes); }
    public static void registerAllThemes() { CliqueStyles.registerAllThemes(); }
    public static List<CliqueTheme> discoverThemes() { return CliqueStyles.discoverThemes(); }
    public static Optional<CliqueTheme> findTheme(String name) { return CliqueStyles.findTheme(name); }

    // DEPRECATED

    /**
     * @deprecated As of 3.1, use {@link #table(TableType)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable(TableType type) { return CliqueComponents.customizableTable(type); }
    /**
     * @deprecated As of 3.1, use {@link #table(TableType, TableConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable(TableType type, TableConfiguration configuration) { return CliqueComponents.customizableTable(type, configuration); }

    /**
     * @deprecated As of 3.1, use {@link #table()} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable() { return CliqueComponents.customizableTable(); }
    /**
     * @deprecated As of 3.1, use {@link #table(TableConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableTableHeaderBuilder customizableTable(TableConfiguration configuration) { return CliqueComponents.customizableTable(configuration); }

    /**
     * @deprecated As of 3.1, use {@link #box(BoxType)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type) {
        return BoxFactory.getCustomizableBoxDimensionBuilder(type);
    }

    /**
     * @deprecated As of 3.1, use {@link #box(BoxType, BoxConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxType type, BoxConfiguration configuration) { return CliqueComponents.customizableBox(type, configuration); }

    /**
     * @deprecated As of 3.1, use {@link #box()} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox() {
        return CliqueComponents.customizableBox(); }

    /**
     * @deprecated As of 3.1, use {@link #box(BoxConfiguration)} instead. This will be removed in a future release.
     */
    @Deprecated(forRemoval = true, since = "3.1")
    public static CustomizableBoxDimensionBuilder customizableBox(BoxConfiguration configuration) { return CliqueComponents.customizableBox(configuration); }
}