package boxes.concrete;

public class RoundedBox extends ClassicBox {

    public RoundedBox(){
        super();
    }

    public RoundedBox(int width, int length, String content) {
        super(width, length, content);
        this.topLeft = "╭";
        this.topRight = "╮";
        this.bottomLeft = "╰";
        this.bottomRight = "╯";
    }
}
