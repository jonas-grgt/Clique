package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.TableConfiguration;

public class RoundedBoxDrawTable extends BoxDrawTable{
        public RoundedBoxDrawTable(TableConfiguration config) {
            super(config);
            this.topLeft = "╭";
            this.topRight = "╮";
            this.bottomLeft = "╰";
            this.bottomRight = "╯";
            this.styleTableBorders();
        }
}
