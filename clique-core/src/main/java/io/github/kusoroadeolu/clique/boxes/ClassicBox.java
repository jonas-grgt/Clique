package io.github.kusoroadeolu.clique.boxes;


import io.github.kusoroadelu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.drawBox;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.handleDimensionsEx;

public class ClassicBox extends AbstractBox {

    protected String topLeft;
    protected String topRight;
    protected String bottomLeft;
    protected String bottomRight;

    public ClassicBox(){
        super();
        this.initBorders();
        this.styleBox();
    }

    public ClassicBox(BoxConfiguration configuration) {
        super(configuration);
        this.initBorders();
        this.styleBox();

    }

    public ClassicBox(int width, int length, String content) {
        super(width, length, content);
        this.initBorders();
    }

    public String get() {
        return handleDimensionsEx(() -> {
            this.wrapWord();
            final StringBuilder sb = new StringBuilder();
            final BoxWrapper wrapper = new BoxWrapper(
                    this.width,
                    this.length,
                    this.boxConfiguration,
                    this.contentWrap,
                    this.hLine,
                    this.vLine,
                    this.topLeft,
                    this.topRight,
                    this.bottomRight,
                    this.bottomLeft
            );
            drawBox(sb, wrapper);
            return sb.toString();
        });
    }

    protected void styleBox(){
        if(this.boxConfiguration.getBorderStyle() != null){
            final BorderStyle borderStyle = this.boxConfiguration.getBorderStyle();
            final StyleBuilder sb = borderStyle.styleBuilder();
            final AnsiCode[] horizontalStyles = borderStyle.getHorizontalBorderStyles();
            final AnsiCode[] verticalStyles = borderStyle.getVerticalBorderStyles();
            final AnsiCode[] edgeStyles = borderStyle.getEdgeBorderStyles();

            this.hLine = sb.formatAndReset(this.hLine, horizontalStyles);
            this.vLine = sb.formatAndReset(this.vLine, verticalStyles);
            this.topLeft = sb.formatAndReset(this.topLeft, edgeStyles);
            this.topRight = sb.formatAndReset(this.topRight, edgeStyles);
            this.bottomLeft = sb.formatAndReset(this.bottomLeft, edgeStyles);
            this.bottomRight = sb.formatAndReset(this.bottomRight, edgeStyles);

        }
    }

    private void initBorders(){
        this.hLine = "─";
        this.vLine = "│";
        this.topLeft = "┌";
        this.topRight = "┐";
        this.bottomLeft = "└";
        this.bottomRight = "┘";
    }
}
