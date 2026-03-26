package io.github.kusoroadeolu.clique.spi;

import java.util.Map;

public interface CliqueTheme {
    /**
     * @return The name of the theme
     * */
    String themeName();

    /**
     * @return A map of ansi code styles contained by the theme
     * */
    Map<String, AnsiCode> styles();

    /**
     * @return The author of the theme
     * */
    default String author(){
        return "";
    }

    /**
     * @return The repository url of the theme
     * */
    default String url(){
        return "";
    }
}
