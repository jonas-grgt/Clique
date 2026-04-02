package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.config.TreeConfiguration;
import io.github.kusoroadeolu.clique.tree.Tree;

public class Main {
    public static void main(String[] args) {
        TreeConfiguration config = TreeConfiguration.builder()
                .guideStyle("*cyan, bold") //Do not add the markup tag borders i.e [*cyan, bold]
                .build();

        Tree tree = Clique.tree("[*magenta, bold]clique-lib/", config);

        Tree src = tree.add("[*cyan, bold]src/");
        Tree core = src.add("[cyan]core/");
        core.add("[green]Parser.java         [dim]✓ 312 lines");
        core.add("[green]StyleResolver.java  [dim]✓ 198 lines");
        core.add("[yellow]Renderer.java       [dim]⚠ needs review");

        Tree tests = tree.add("[*cyan, bold]tests/");
        tests.add("[green, bold]ParserTest.java     [dim]✓ 14/14 pass");
        tests.add("[red, bold]RendererTest.java   [dim]✗  9/14 pass");
        tests.add("[dim, strike]TreeTest.java       skipped");

        tree.add("[white]README.md");
        tree.add("[dim].gitignore");

        tree.print();

    }
}
