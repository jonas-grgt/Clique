package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.Box;
import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.frames.DefaultFrame;
import io.github.kusoroadeolu.clique.indent.Indenter;
import io.github.kusoroadeolu.clique.tables.Table;

public class Main {
    public static void main(String[] args) {
        Box box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 5)
                .content("A wider, shorter box");

        Indenter indenter = Clique.indenter()
                .indent()  // Create first indent level
                .add("Root Level")
                .indent()  // Nest deeper
                .add("Nested Item 1")
                .add("Nested Item 2")
                .unindent()  // Go back up
                .add("Back to Root");

        indenter.print();

        DefaultFrame frame = DefaultFrame
                .builder()
                .nest("Hello")
                .width(50)
                .title("Heyy")
                .nest("123")
                .nest(indenter.get())
                .build();
        frame.render();

//        DefaultFrame frame1 = DefaultFrame
//                .builder()
//                .nest(frame)
//                .build();
//
//        frame1.render();
    }
}
