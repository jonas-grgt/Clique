package io.github.kusoroadeolu.clique.tables.structures;

import io.github.kusoroadeolu.clique.core.utils.CharWidth;

public record Cell(String text, String styledText) {
    public int width(){
        return CharWidth.of(text);
    }

}