package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.components.*;
import io.github.kusoroadeolu.clique.configuration.*;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.utils.AnsiDetector;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import io.github.kusoroadeolu.clique.spi.RGBAnsiCode;
import io.github.kusoroadeolu.clique.style.Ink;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Main facade that delegates to CliqueStyles and CliqueComponents.
 */
@Stable(since = "3.2.0")
public final class Clique {

    private Clique() {
        throw new AssertionError();
    }

    // STYLE BUILDER
    public static StyleBuilder styleBuilder() { return CliqueStyles.styleBuilder(); }

    // PARSER
    public static MarkupParser parser() { return CliqueStyles.parser(); }
    public static MarkupParser parser(ParserConfiguration configuration) { return CliqueStyles.parser(configuration); }

    //INK
    public static Ink ink(){
        return CliqueStyles.ink();
    }

    public static Ink ink(StyleContext context){
        return CliqueStyles.ink(context);
    }



    // TABLE
    public static PendingTable table() { return CliqueComponents.table(); }
    public static PendingTable table(TableConfiguration configuration) { return CliqueComponents.table(configuration); }
    public static PendingTable table(TableType type) { return CliqueComponents.table(type); }
    public static PendingTable table(TableType type, TableConfiguration configuration) { return CliqueComponents.table(type, configuration); }

    public static PendingTable table(TableType type, AnsiCode... borderColor){return CliqueComponents.table(type, borderColor);}

    public static PendingTable table(AnsiCode... borderColor){return CliqueComponents.table(borderColor);}

    public static PendingTable table(TableType type, String borderColor){return CliqueComponents.table(type, borderColor);}

    public static PendingTable table(String borderColor){return CliqueComponents.table(borderColor);}


    // BOX
    public static Box box() { return CliqueComponents.box(); }
    public static Box box(BoxConfiguration configuration) { return CliqueComponents.box(configuration); }
    public static Box box(BoxType type, BoxConfiguration configuration) { return CliqueComponents.box(type, configuration); }
    public static Box box(BoxType type) { return CliqueComponents.box(type); }

    public static Box box(BoxType type, AnsiCode... borderColor){ return CliqueComponents.box(type, borderColor);}

    public static Box box(AnsiCode... borderColor){return CliqueComponents.box(borderColor);}

    public static Box box(BoxType type, String borderColor){ return CliqueComponents.box(type, borderColor);}

    public static Box box(String borderColor){return CliqueComponents.box(borderColor);}

    // ITEM LIST
    public static ItemList list() { return CliqueComponents.list(); }
    public static ItemList list(ItemListConfiguration itemListConfiguration) { return CliqueComponents.list(itemListConfiguration); }


    // PROGRESS BAR
    public static ProgressBar progressBar(int total) { return CliqueComponents.progressBar(total); }
    public static ProgressBar progressBar(int total, ProgressBarConfiguration configuration) { return CliqueComponents.progressBar(total, configuration); }
    public static ProgressBar progressBar(int total, ProgressBarPreset preset) { return CliqueComponents.progressBar(total, preset); }

    public static ProgressBar progressBar(int total, EasingConfiguration easing){
        return CliqueComponents.progressBar(total, easing);
    }

    public static <T> IterableProgressBar<T> progressBar(Collection<T> collection) {
        return CliqueComponents.progressBar(collection);
    }

    public static <T>IterableProgressBar<T> progressBar(Collection<T> collection, ProgressBarConfiguration configuration) {
        return CliqueComponents.progressBar(collection, configuration);
    }

    public static  <T>IterableProgressBar<T> progressBar(Collection<T> collection, ProgressBarPreset preset) {
        return CliqueComponents.progressBar(collection, preset.getConfiguration());
    }


    // FRAME
    public static Frame frame() { return CliqueComponents.frame(); }
    public static Frame frame(FrameConfiguration configuration) { return CliqueComponents.frame(configuration); }
    public static Frame frame(BoxType type) { return CliqueComponents.frame(type); }
    public static Frame frame(BoxType type, FrameConfiguration configuration) { return CliqueComponents.frame(type, configuration); }

    public static Frame frame(BoxType type, AnsiCode... borderColor){return CliqueComponents.frame(type, borderColor);}

    public static Frame frame(AnsiCode... borderColor){return CliqueComponents.frame(borderColor);}

    public static Frame frame(BoxType type, String borderColor){return CliqueComponents.frame(type, borderColor);}

    public static Frame frame(String borderColor){return CliqueComponents.frame(borderColor);}

    // TREE
    public static Tree tree(String label) { return CliqueComponents.tree(label); }
    public static Tree tree(String label, TreeConfiguration configuration) { return CliqueComponents.tree(label, configuration); }

    public static Tree tree(String label, String connectorColor) { return CliqueComponents.tree(label, connectorColor); }
        public static Tree tree(String label, AnsiCode... connectorColor) { return CliqueComponents.tree(label, connectorColor); }

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
    public static java.util.List<CliqueTheme> discoverThemes() { return CliqueStyles.discoverThemes(); }
    public static Optional<CliqueTheme> findTheme(String name) { return CliqueStyles.findTheme(name); }

}