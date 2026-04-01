package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import io.github.kusoroadeolu.clique.progressbar.ProgressBar;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .styleRange(0, 30, "[red]:bar[/] :percent% [red]Starting...[/]")
                .styleRange(30, 70, "[yellow]:bar[/] :percent% [yellow]In Progress...[/]")
                .styleRange(70, 100, "[green]:bar[/] :percent% [green]Almost Done![/]")
                .build();

        ProgressBar bar = Clique.progressBar(100, config);
        while (!bar.isDone()){
            bar.tick();
            Thread.sleep(100);
        }
    }
}
