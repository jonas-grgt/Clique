package boxes.concrete;

import boxes.configuration.BoxConfiguration;

public class RoundedBox extends ClassicBox {

    public RoundedBox(){
        super();
        this.initBorders();
    }

    public RoundedBox(BoxConfiguration configuration) {
        super(configuration);
        this.initBorders();
    }

    public RoundedBox(int width, int length, String content) {
        super(width, length, content);
        this.initBorders();

    }

    public void initBorders(){
        this.topLeft = "╭";
        this.topRight = "╮";
        this.bottomLeft = "╰";
        this.bottomRight = "╯";
    }
}
