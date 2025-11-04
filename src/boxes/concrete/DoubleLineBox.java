package boxes.concrete;

public class DoubleLineBox extends ClassicBox {

    public DoubleLineBox(){
        super();
    }

    public DoubleLineBox(int width, int length, String content) {
        super(width, length, content);
        this.topLeft = "╔";
        this.topRight = "╗";
        this.bottomLeft = "╚";
        this.bottomRight = "╝";
        this.vLine = "║";
        this.hLine = "═";
    }

}
