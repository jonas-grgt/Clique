package com.github.kusoroadeolu.clique.themes;

import java.util.*;

public class CliqueThemes {
    private final static Map<String, CliqueTheme> THEMES = new HashMap<>();

    private CliqueThemes(){}

    public static List<CliqueTheme> discover(){
        return ServiceLoader.load(CliqueTheme.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    public static Optional<CliqueTheme> find(String name) {
        Objects.requireNonNull(name, "Theme name cannot be null");
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
        find(name).ifPresent(CliqueTheme::register);
    }

    public static void registerThemes(String... names){
        for (String name : names){
            var optional = find(name);
            optional.ifPresent(CliqueTheme::register);
        }
    }

    public static void registerThemes(Collection<String> names){
        for (String name : names){
            var optional = find(name);
            optional.ifPresent(CliqueTheme::register);
        }
    }

    public static void registerAll() {
        discover().forEach(CliqueTheme::register);
    }
}
