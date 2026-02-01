package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;

import static java.lang.Thread.sleep;

class Main {
    public static void main(String[] args) throws InterruptedException {
        ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
                .complete('>')
                .incomplete('.')
                .format("[:bar] :percent%")
                .length(50)
                .build();

        ProgressBar bar = Clique.progressBar(100, config);
        for (int i = 0; i < 100; i++){
            bar.tick();
            bar.render();
            Thread.sleep(100);
        }

// Custom predicates
        ProgressBarConfiguration advanced = ProgressBarConfiguration.immutableBuilder()
                .styleWhen(p -> p < 25, "[red, bold]:bar[/] [red]:percent% - SLOW![/]")
                .styleWhen(p -> p >= 25 && p < 75, "[blue]:bar[/] :percent%")
                .styleWhen(p -> p >= 75 && p < 100, "[green]:bar[/] :percent%")
                .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]COMPLETE![/]")
                .build();

    }
}



