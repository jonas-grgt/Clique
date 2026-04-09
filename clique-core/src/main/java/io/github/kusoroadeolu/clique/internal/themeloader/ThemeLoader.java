package io.github.kusoroadeolu.clique.internal.themeloader;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.internal.markup.GlobalStyleRegistry;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

@InternalApi(since = "3.2.0")
public class ThemeLoader {
    private final static Map<String, CliqueTheme> THEMES = new ConcurrentHashMap<>();

    private ThemeLoader() {}

    public static List<CliqueTheme> findAvailableThemes() {
        return ServiceLoader.load(CliqueTheme.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    public static Optional<CliqueTheme> find(String name) {
        requireNonNull(name, "Theme name cannot be null");
        return Optional.ofNullable(
                THEMES.computeIfAbsent(name, k ->
                        findAvailableThemes().stream()
                                .filter(t -> t.themeName().equals(k))
                                .findFirst()
                                .orElse(null)
                )
        );
    }

    public static void register(CliqueTheme theme) {
       Objects.requireNonNull(theme, "Clique theme cannot be null");
       GlobalStyleRegistry.registerStyles(theme.styles());
    }

    public static void register(String name) {
        find(name).ifPresent(t -> GlobalStyleRegistry.registerStyles(t.styles()));
    }

    public static void registerThemes(String... names) {
        for (String name : names) {
            var optional = find(name);
            optional.ifPresent(t -> GlobalStyleRegistry.registerStyles(t.styles()));
        }
    }

    public static void registerThemes(Collection<String> names) {
        for (String name : names) {
            var optional = find(name);
            optional.ifPresent(t -> GlobalStyleRegistry.registerStyles(t.styles()));
        }
    }

    public static void registerAll() {
        findAvailableThemes().forEach(t -> Clique.registerStyles(t.styles()));
    }
}
