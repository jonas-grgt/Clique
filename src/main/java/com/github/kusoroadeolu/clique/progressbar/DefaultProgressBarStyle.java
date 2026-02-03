package com.github.kusoroadeolu.clique.progressbar;

import com.github.kusoroadeolu.clique.config.ProgressBarConfiguration;

/**
 * Predefined progress bar configs with sensible defaults.
 */
public enum DefaultProgressBarStyle implements ProgressBarStyle {

    BLOCKS {
        @Override
        public ProgressBarConfiguration getConfiguration() {
            return ProgressBarConfiguration.immutableBuilder()
                .length(40)
                .complete('█')
                .incomplete('░')
                .format(":bar :percent% [:elapsed/:remaining]")
                .build();
        }
    },
    

    LINES {
        @Override
        public ProgressBarConfiguration getConfiguration() {
            return ProgressBarConfiguration.immutableBuilder()
                .length(50)
                .complete('▂')
                .incomplete('▁')
                .format(":bar :percent%")
                .build();
        }
    },
    

    BOLD {
        @Override
        public ProgressBarConfiguration getConfiguration() {
            return ProgressBarConfiguration.immutableBuilder()
                .length(40)
                .complete('▰')
                .incomplete('▱')
                .format(":bar :percent% | :progress/:total")
                .build();
        }
    },
    

    CLASSIC {
        @Override
        public ProgressBarConfiguration getConfiguration() {
            return ProgressBarConfiguration.immutableBuilder()
                .length(50)
                .complete('#')
                .incomplete('=')
                .format("[:bar] :percent% [:elapsed]")
                .build();
        }
    },

    DOTS {
        @Override
        public ProgressBarConfiguration getConfiguration() {
            return ProgressBarConfiguration.immutableBuilder()
                .length(50)
                .complete('●')
                .incomplete('○')
                .format(":bar :percent%")
                .build();
        }
    };


}