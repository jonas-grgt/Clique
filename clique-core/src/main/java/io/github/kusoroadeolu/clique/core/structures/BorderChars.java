package io.github.kusoroadeolu.clique.core.structures;

import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

@InternalApi(since = "3.2.0")
public class BorderChars {
    private String hLine;
    private String vLine;
    private String topLeft;
    private String topRight;
    private String bottomLeft;
    private String bottomRight;

    BorderChars(String hLine, String vLine, String topLeft, String topRight, String bottomLeft, String bottomRight) {
        this.hLine = hLine;
        this.vLine = vLine;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public static BorderChars from(BoxType type) {
        return switch (type) {
            case DEFAULT      -> new BorderChars("-", "|", "+", "+", "+", "+");
            case DOUBLE_LINE  -> new BorderChars("═", "║", "╔", "╗", "╚", "╝");
            case CLASSIC      -> new BorderChars("─", "│", "┌", "┐", "└", "┘");
            case ROUNDED      -> new BorderChars("─", "│", "╭", "╮", "╰", "╯");
        };
    }

    public String hLine()      { return hLine; }
    public String vLine()      { return vLine; }
    public String topLeft()    { return topLeft; }
    public String topRight()   { return topRight; }
    public String bottomLeft() { return bottomLeft; }
    public String bottomRight(){ return bottomRight; }

    public void setHLine(String hLine)           { this.hLine = hLine; }
    public void setVLine(String vLine)           { this.vLine = vLine; }
    public void setTopLeft(String topLeft)       { this.topLeft = topLeft; }
    public void setTopRight(String topRight)     { this.topRight = topRight; }
    public void setBottomLeft(String bottomLeft) { this.bottomLeft = bottomLeft; }
    public void setBottomRight(String bottomRight){ this.bottomRight = bottomRight; }
    public void setCorners(String edge){
        setTopLeft(edge);
        setTopRight(edge);
        setBottomLeft(edge);
        setBottomRight(edge);
    }

}