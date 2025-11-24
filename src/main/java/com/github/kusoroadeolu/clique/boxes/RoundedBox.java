package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;

public class RoundedBox extends ClassicBox {

    public RoundedBox(){
        super();
        this.initBorders();
        this.styleBox();
    }

    public RoundedBox(BoxConfiguration configuration) {
        super(configuration);
        this.initBorders();
        this.styleBox();
    }

    public RoundedBox(int width, int length, String content) {
        super(width, length, content);
        this.initBorders();
        this.styleBox();
    }

    public void initBorders(){
        this.topLeft = "╭";
        this.topRight = "╮";
        this.bottomLeft = "╰";
        this.bottomRight = "╯";
    }
}
