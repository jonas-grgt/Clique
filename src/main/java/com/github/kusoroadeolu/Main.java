package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;

import static java.lang.Thread.sleep;

class Main {
    public static void main(String[] args) throws InterruptedException {

        ProgressBar pg = new ProgressBar(100);
        for (int i = 0; i < 100; ++i){
            pg.tick();
            pg.render();
            sleep(100);
        }

    }
}



