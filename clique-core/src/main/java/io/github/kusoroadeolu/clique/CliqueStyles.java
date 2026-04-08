package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.configuration.ParserConfiguration;
import io.github.kusoroadeolu.clique.configuration.StyleContext;
import io.github.kusoroadeolu.clique.internal.RGBColor;
import io.github.kusoroadeolu.clique.internal.markup.GlobalStyleRegistry;
import io.github.kusoroadeolu.clique.internal.themeloader.CliqueThemeLoader;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import io.github.kusoroadeolu.clique.spi.RGBAnsiCode;
import io.github.kusoroadeolu.clique.style.Ink;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sub-facade for style, parsing, RGB, and theme-related functionality.
 */
final class CliqueStyles {

    private CliqueStyles() {}

    // STYLE BUILDER

    public static StyleBuilder styleBuilder() {
        return new StyleBuilder();
    }

    // PARSER

    public static MarkupParser parser() {
        return new MarkupParser();
    }

    public static MarkupParser parser(ParserConfiguration configuration) {
        return new MarkupParser(configuration);
    }

    public static Ink ink() {
        return new Ink();
    }

    public static Ink ink(StyleContext context) {
        return new Ink(context);
    }


    // RGB

    public static RGBAnsiCode rgb(int r, int g, int b) {
        return new RGBColor(r, g, b, false);
    }

    public static RGBAnsiCode rgb(int r, int g, int b, boolean background) {
        return new RGBColor(r, g, b, background);
    }

    // STYLE REGISTRATION

    public static void registerStyle(String style, AnsiCode code) {
        GlobalStyleRegistry.registerStyle(style, code);
    }

    public static void registerStyles(Map<String, AnsiCode> codes) {
        GlobalStyleRegistry.registerStyles(codes);
    }

    // THEMES

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