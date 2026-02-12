package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadelu.clique.spi.AnsiCode;

import java.util.Map;

public class GlobalParserRegistry {
    private GlobalParserRegistry(){
        throw new AssertionError();
    }

    public static void registerStyle(String style, AnsiCode code){
        StyleMaps.GLOBAL_CUSTOM_CODES.put(style, code);
    }

    public static void registerStyles(Map<String, AnsiCode> codes){
        StyleMaps.GLOBAL_CUSTOM_CODES.putAll(codes);
    }
}
