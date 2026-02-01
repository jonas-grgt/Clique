package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;

import static java.lang.Thread.sleep;

class Main {
    public static void main(String[] args) throws InterruptedException {



// Custom predicates
        ProgressBarConfiguration advanced = ProgressBarConfiguration.immutableBuilder()
                .styleWhen(p -> p < 25, "[red, bold]:bar[/] [red]:percent% - SLOW![/]")
                .styleWhen(p -> p >= 25 && p < 75, "[blue]:bar[/] :percent%")
                .styleWhen(p -> p >= 75 && p < 100, "[green]:bar[/] :percent%")
                .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]COMPLETE![/]")
                .build();

        ProgressBar bar = Clique.progressBar(100, advanced);
        while (!bar.isDone()){
            bar.tick();
            bar.render();
            Thread.sleep(100);
        }

    }

}



