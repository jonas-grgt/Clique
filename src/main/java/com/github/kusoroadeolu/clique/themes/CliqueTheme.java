package com.github.kusoroadeolu.clique.themes;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.ansi.AnsiCode;

import java.util.Map;

public interface CliqueTheme {
    String themeName();
    Map<String, AnsiCode> styles();
    default void register(){
        Clique.registerStyles(this.styles());
    }
}
