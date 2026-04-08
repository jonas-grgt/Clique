package io.github.kusoroadeolu.clique.configuration;


import io.github.kusoroadeolu.clique.internal.documentation.Stable;


/**
* Predefined progress bar configs with sensible defaults.
 * @since 3.0.0
 * */
@Stable(since = "3.2.0")
public enum ProgressBarPreset {

    BLOCKS {
        @Override
        public ProgressBarConfiguration getConfiguration() {
            return ProgressBarConfiguration
                    .builder()
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
            return ProgressBarConfiguration.builder()
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
            return ProgressBarConfiguration.builder()
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
            return ProgressBarConfiguration.builder()
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
            return ProgressBarConfiguration.builder()
                    .length(50)
                    .complete('●')
                    .incomplete('○')
                    .format(":bar :percent%")
                    .build();
        }
    };


    public abstract ProgressBarConfiguration getConfiguration();
}