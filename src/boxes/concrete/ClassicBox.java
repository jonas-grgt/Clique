package boxes.concrete;

import boxes.BoxWrapper;
import boxes.abstractboxes.AbstractBox;
import core.ansi.interfaces.AnsiCode;
import core.misc.BorderStyle;
import core.style.StyleBuilder;

import static core.utils.BoxUtils.drawBox;

public class ClassicBox extends AbstractBox {

    protected String topLeft;
    protected String topRight;
    protected String bottomLeft;
    protected String bottomRight;

    public ClassicBox(){
        super();
    }

    public ClassicBox(int width, int length, String content) {
        super(width, length, content);
        this.hLine = "─";
        this.vLine = "│";
        this.topLeft = "┌";
        this.topRight = "┐";
        this.bottomLeft = "└";
        this.bottomRight = "┘";
    }

    public String buildBox() {
        this.styleBox();
        this.wrapWord();
        System.out.println(width);
        final StringBuilder sb = new StringBuilder();
        final BoxWrapper wrapper = new BoxWrapper(
                this.width,
                this.length,
                this.boxConfiguration,
                this.wordWrap,
                this.hLine,
                this.vLine,
                this.topLeft,
                this.topRight,
                this.bottomRight,
                this.bottomLeft
        );
        drawBox(sb, wrapper);
        return sb.toString();
    }

    private void styleBox(){
        if(this.boxConfiguration.getBorderStyle() != null){
            final BorderStyle borderStyle = this.boxConfiguration.getBorderStyle();
            final StyleBuilder sb = BorderStyle.styleBuilder();
            final AnsiCode[] horizontalStyles = borderStyle.getHorizontalBorderStyles();
            final AnsiCode[] verticalStyles = borderStyle.getVerticalBorderStyles();
            final AnsiCode[] edgeStyles = borderStyle.getEdgeBorderStyles();

            this.hLine = sb.formatReset(this.hLine, horizontalStyles);
            this.vLine = sb.formatReset(this.vLine, verticalStyles);
            this.topLeft = sb.formatReset(this.topLeft, edgeStyles);
            this.topRight = sb.formatReset(this.topRight, edgeStyles);
            this.bottomLeft = sb.formatReset(this.bottomLeft, edgeStyles);
            this.bottomRight = sb.formatReset(this.bottomRight, edgeStyles);

        }
    }
}
