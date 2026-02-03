package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.config.EasingConfiguration;
import com.github.kusoroadeolu.clique.config.EasingFunction;
import com.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;

class Main {
    public static void main(String[] args)  {
        EasingConfiguration easing = EasingConfiguration.immutableBuilder()
                .function(EasingFunction.EASE_OUT_QUAD)
                .duration(500)
                .frames(30)
                .threshold(10)  // only ease ticks >= 10
                .build();

        ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
                .easing(easing)
                .build();

        ProgressBar bar = Clique.progressBar(120, config);
        bar.tickAnimated(100);
    }
}