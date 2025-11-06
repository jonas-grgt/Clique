package com.github.kusoroadeolu.tables.concrete;

import com.github.kusoroadeolu.tables.configuration.TableConfiguration;

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
