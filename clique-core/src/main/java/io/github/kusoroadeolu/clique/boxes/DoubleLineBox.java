package io.github.kusoroadeolu.clique.boxes;


import io.github.kusoroadeolu.clique.config.BoxConfiguration;

public class DoubleLineBox extends ClassicBox {

    public DoubleLineBox() {
        super();
        this.initDLBorders();
    }

    public DoubleLineBox(int width, int length, String content) {
        super(width, length, content);
        this.initDLBorders();
        this.styleBox();
    }

    public DoubleLineBox(BoxConfiguration configuration) {
        super(configuration);
        this.initDLBorders();
        this.styleBox();

    }

    private void initDLBorders() {
        this.topLeft = "╔";
        this.topRight = "╗";
        this.bottomLeft = "╚";
        this.bottomRight = "╝";
        this.vLine = "║";
        this.hLine = "═";
    }

}
