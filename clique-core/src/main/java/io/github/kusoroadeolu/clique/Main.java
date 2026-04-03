package io.github.kusoroadeolu.clique;

public class Main {
    public static void main(String[] args) {
        Clique.box().autosize()
                .content("""
                        \\[red]Hello Guys
                        [red]I'm here to handle your software[/]
                        [blue] Pls give me perms. I am not a hacker
                        """)
                .render();
    }
}
