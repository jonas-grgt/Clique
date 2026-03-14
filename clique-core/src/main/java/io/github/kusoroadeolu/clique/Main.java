package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.ansi.ColorCode;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;

public class Main {
    public static void main(String[] args) {
        Clique.box(BoxType.CLASSIC)
                .withDimensions(20, 10)
                .content("👨‍👦 family emoji in a manually sized box with some extra text to force wrapping")
                .render();
    }
}
