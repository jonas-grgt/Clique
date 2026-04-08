package io.github.kusoroadeolu.clique.components;


import io.github.kusoroadeolu.clique.configuration.TableConfiguration;

public class RoundedBoxDrawTable extends BoxDrawTable {
    public RoundedBoxDrawTable(TableConfiguration config) {
        super(config);
        this.topLeft = "╭";
        this.topRight = "╮";
        this.bottomLeft = "╰";
        this.bottomRight = "╯";
        this.colorTableBorders();
    }
}
