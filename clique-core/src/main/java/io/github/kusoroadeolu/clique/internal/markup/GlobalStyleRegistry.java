package io.github.kusoroadeolu.clique.internal.markup;


import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Map;
import java.util.Objects;

@InternalApi(since = "3.2.0")
public class GlobalStyleRegistry {
    private GlobalStyleRegistry() {}

    public static void registerStyle(String style, AnsiCode code) {
        Objects.requireNonNull(style, "Style cannot be null");
        Objects.requireNonNull(code, "Ansi code cannot be null");
        PredefinedStyleContext.CUSTOM_STYLE_CODES.put(style, code);
    }

    public static void registerStyles(Map<String, AnsiCode> codes) {
        Objects.requireNonNull(codes, "Style map cannot be null");
        PredefinedStyleContext.CUSTOM_STYLE_CODES.putAll(codes);
    }


}
