package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Map;

public class GlobalStyleRegistry {
    private GlobalStyleRegistry(){
        throw new AssertionError();
    }

    public static void registerStyle(String style, AnsiCode code){
        StyleMaps.CUSTOM_STYLE_CODES.put(style, code);
    }

    public static void registerStyles(Map<String, AnsiCode> codes){
        StyleMaps.CUSTOM_STYLE_CODES.putAll(codes);
    }
}
