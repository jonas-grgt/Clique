package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.frame.Frame;
import io.github.kusoroadeolu.clique.frame.FrameType;
import io.github.kusoroadeolu.clique.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree tree = Clique.tree("[*magenta, bold]clique-lib/");
        tree.add(tree);
        tree.print();
//        Frame frame = Clique.frame(FrameType.ROUNDED).nest(tree);
//        frame.render();
//
//        frame.nest(frame).render();
    }
}
