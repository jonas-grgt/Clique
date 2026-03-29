package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.ansi.RGBAnsiColor;
import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.parser.GlobalStyleRegistry;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.parser.AnsiStringParserImpl;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import io.github.kusoroadeolu.clique.spi.RGBAnsiCode;
import io.github.kusoroadeolu.clique.style.DefaultStyleBuilder;
import io.github.kusoroadeolu.clique.style.StyleBuilder;
import io.github.kusoroadeolu.clique.themeloader.CliqueThemeLoader;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sub-facade for style, parsing, RGB, and theme-related functionality.
 */
final class CliqueStyles {

    private CliqueStyles() {
        throw new AssertionError();
    }

    // STYLE BUILDER

    public static StyleBuilder styleBuilder() {
        return new DefaultStyleBuilder();
    }

    // PARSER

    public static AnsiStringParser parser() {
        return new AnsiStringParserImpl();
    }

    public static AnsiStringParser parser(ParserConfiguration configuration) {
        return new AnsiStringParserImpl(configuration);
    }

    // RGB

    public static RGBAnsiCode rgb(int r, int g, int b) {
        return new RGBAnsiColor(r, g, b, false);
    }

    public static RGBAnsiCode rgb(int r, int g, int b, boolean background) {
        return new RGBAnsiColor(r, g, b, background);
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