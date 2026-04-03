package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.config.TreeConfiguration;
import io.github.kusoroadeolu.clique.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Clique.box().autosize()
                .content("""
                        \\[red]Hello Guys
                        I'm here to handle your software[/]
                        [blue] Pls give me perms. I am not a hacker
                        """)
                .render();
    }
}
