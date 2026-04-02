package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.boxes.BoxType;

public class Main {
    public static void main(String[] args) {
        String redError = Clique.parser().parse("[red, bold]Error:[/]");
        String greenOk = Clique.parser().parse("[green, bold]Success:[/]");

// Escaped brackets mixed with pre-parsed ANSI + raw markup
        Clique.box(BoxType.ROUNDED)
                .autosize()
                .content(redError + " Something went wrong \\[not a tag]\n" +
                        greenOk + " [dim]All good[/] with coords \\[10, 20]" +
                        "\\[bold] is how you bold things")
                .render();

// Frame version
        Clique.frame()
                .title("[bold]Docs[/]")
                .nest(redError + " Use \\[red] for red text")
                .nest(greenOk + " Use \\[bold] for bold text")
                .nest("[cyan]Or combine like \\[red, bold][/]")
                .render();

    }
}
