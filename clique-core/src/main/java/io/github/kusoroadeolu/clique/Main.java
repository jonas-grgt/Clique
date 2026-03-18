package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.config.BoxConfiguration;

public class Main {
    public static void main(String[] args) {
        Clique.box(BoxConfiguration.immutableBuilder().centerPadding(2).build())
                .withDimensions(10, 5)
                .content("Hello".repeat(2))
                .render();
    }

    }
