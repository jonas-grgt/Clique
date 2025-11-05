package boxes.concrete;

import boxes.BoxWrapper;
import boxes.configuration.BoxConfiguration;
import boxes.interfaces.CustomizableBox;
import boxes.abstractboxes.AbstractBox;
import core.ansi.interfaces.AnsiCode;
import core.misc.BorderStyle;
import core.style.StyleBuilder;

import static core.utils.BoxUtils.drawBox;

public class DefaultBox extends AbstractBox implements CustomizableBox {

    public DefaultBox(int width, int length, String content) {
        super(width, length, content);
    }

    public DefaultBox(BoxConfiguration configuration) {
        super(configuration);
    }

    public DefaultBox(){
        super();
    }

    public String buildBox() {
        this.wrapWord();
        this.styleBox();
        final StringBuilder sb = new StringBuilder();
        final BoxWrapper wrapper = new BoxWrapper(
                this.width, this.length, this.boxConfiguration,
                this.wordWrap, this.hLine, this.vLine,
                this.edge, this.edge, this.edge, this.edge
        );
        drawBox(sb, wrapper);
        return sb.toString();
    }

    public CustomizableBox customizeEdge(char edge) {
        this.edge = String.valueOf(edge);
        return this;
    }

    public CustomizableBox customizeVerticalLine(char vLine) {
        this.vLine = String.valueOf(vLine);
        return this;
    }

    public CustomizableBox customizeHorizontalLine(char hLine) {
        this.hLine = String.valueOf(hLine);
        return this;
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
            this.edge = sb.formatReset(this.edge, edgeStyles);

        }
    }

}

