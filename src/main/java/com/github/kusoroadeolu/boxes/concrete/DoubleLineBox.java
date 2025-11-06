package com.github.kusoroadeolu.boxes.concrete;

import com.github.kusoroadeolu.boxes.configuration.BoxConfiguration;

public class DoubleLineBox extends ClassicBox {

    public DoubleLineBox(){
        super();
        this.initBorders();
    }

    public DoubleLineBox(int width, int length, String content) {
        super(width, length, content);
        this.initBorders();
    }

    public DoubleLineBox(BoxConfiguration configuration) {
        super(configuration);
        this.initBorders();
    }

    private void initBorders(){
        this.topLeft = "╔";
        this.topRight = "╗";
        this.bottomLeft = "╚";
        this.bottomRight = "╝";
        this.vLine = "║";
        this.hLine = "═";
    }

}
