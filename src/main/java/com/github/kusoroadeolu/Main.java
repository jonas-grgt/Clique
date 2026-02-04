package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.boxes.Box;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.indent.Indenter;


class Main {
    public static void main(String[] args)  {
        Indenter indenter = Clique.indenter()
                .indent()  // Create first indent level
                .add("Root Level")
                .indent("-")  // Nest deeper
                .add("Nested Item 1")
                .add("Nested Item 2")
                .unindent()  // Go back up
                .add("Back to Root");

        indenter.print();
    }
}