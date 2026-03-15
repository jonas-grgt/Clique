package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.Box;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.utils.StringUtils;
import io.github.kusoroadeolu.clique.frames.DefaultFrame;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.tables.Table;

public class Main {
    public static void main(String[] args) {
        Box box = Clique.box(BoxType.ROUNDED, BoxConfiguration.immutableBuilder().autoSize().build())
                .noDimensions()
                .content("[red]A wider, shorter box");



        Indenter indenter = Clique.indenter()
                .indent()  // Create first indent level
                .add("Root Level")
                .indent()  // Nest deeper
                .add("[red]Nested Item 1")
                .add("Nested Item 2")
                .unindent()  // Go back up
                .add("Back to Root");

        DefaultFrame frame = DefaultFrame
                .builder()
                .nest("[blue]Hello")
                .width(50)
                .nest(" ".repeat(50))
                .title("Heyy")
                .nest(box)
                .build();
        //frame.render();

        DefaultFrame frame1 = DefaultFrame
                .builder()
                .nest(frame)
                .build();

        frame1.render();
    }
}
