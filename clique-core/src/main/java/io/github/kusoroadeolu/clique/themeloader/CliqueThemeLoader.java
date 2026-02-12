package io.github.kusoroadeolu.clique.themeloader;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadelu.clique.spi.CliqueTheme;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class CliqueThemeLoader {
    private final static Map<String, CliqueTheme> THEMES = new HashMap<>();

    private CliqueThemeLoader(){}

    public static List<CliqueTheme> discover(){
        return ServiceLoader.load(CliqueTheme.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    public static Optional<CliqueTheme> find(String name) {
        requireNonNull(name, "Theme name cannot be null");
        return Optional.ofNullable(
                THEMES.computeIfAbsent(name, k ->
                        discover().stream()
                                .filter(t -> t.themeName().equals(k))
                                .findFirst()
                                .orElse(null)
                )
        );
    }

    public static void register(String name) {
        find(name).ifPresent(t -> Clique.registerStyles(t.styles()));
    }

    public static void registerThemes(String... names){
        for (String name : names){
            var optional = find(name);
            optional.ifPresent(t -> Clique.registerStyles(t.styles()));
        }
    }

    public static void registerThemes(Collection<String> names){
        for (String name : names){
            var optional = find(name);
            optional.ifPresent(t -> Clique.registerStyles(t.styles()));
        }
    }

    public static void registerAll() {
        discover().forEach(t -> Clique.registerStyles(t.styles()));
    }
}
