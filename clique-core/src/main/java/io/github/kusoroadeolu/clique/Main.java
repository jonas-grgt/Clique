package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;

public class Main {
    public static void main(String[] args) {
        BoxConfiguration config = BoxConfiguration.immutableBuilder()
                .autoSize()
                .build();

        Clique.box(BoxType.CLASSIC, config)
                .noDimensions()
                .customize()
                .customizeEdge('*')
                .customizeHorizontalLine('=')
                .customizeVerticalLine('!')
                .content("[cyan]Custom borders on any box type[/]")
                .render();
    }
}
