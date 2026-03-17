package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.config.TableConfiguration;

class RoundedBoxDrawTable extends BoxDrawTable {
    public RoundedBoxDrawTable(TableConfiguration config) {
        super(config);
        this.topLeft = "╭";
        this.topRight = "╮";
        this.bottomLeft = "╰";
        this.bottomRight = "╯";
        this.styleTableBorders();
    }
}
