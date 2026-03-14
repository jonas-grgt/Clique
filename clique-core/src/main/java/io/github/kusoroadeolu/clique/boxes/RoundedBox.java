package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BoxConfiguration;

public class RoundedBox extends ClassicBox {

    public RoundedBox() {
        super();
        this.initRoundedBorders();
        this.styleBox();
    }

    public RoundedBox(BoxConfiguration configuration) {
        super(configuration);
        this.initRoundedBorders();
        this.styleBox();
    }

    public RoundedBox(int width, int length, String content) {
        super(width, length, content);
        this.initRoundedBorders();
        this.styleBox();
    }

    public void initRoundedBorders() {
        this.topLeft = "╭";
        this.topRight = "╮";
        this.bottomLeft = "╰";
        this.bottomRight = "╯";
    }
}
