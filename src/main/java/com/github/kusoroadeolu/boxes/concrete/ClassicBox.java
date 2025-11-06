package com.github.kusoroadeolu.boxes.concrete;

import com.github.kusoroadeolu.boxes.abstractboxes.AbstractBox;
import com.github.kusoroadeolu.boxes.configuration.BoxConfiguration;
import com.github.kusoroadeolu.core.ansi.interfaces.AnsiCode;
import com.github.kusoroadeolu.core.misc.BorderStyle;
import com.github.kusoroadeolu.core.style.StyleBuilder;

import static com.github.kusoroadeolu.core.utils.BoxUtils.drawBox;
import static com.github.kusoroadeolu.core.utils.BoxUtils.handleDimensionsEx;

public class ClassicBox extends AbstractBox {

    protected String topLeft;
    protected String topRight;
    protected String bottomLeft;
    protected String bottomRight;

    public ClassicBox(){
        super();
        this.initBorders();
    }

    public ClassicBox(BoxConfiguration configuration) {
        super(configuration);
        this.initBorders();
    }

    public ClassicBox(int width, int length, String content) {
        super(width, length, content);
        this.initBorders();
    }

    public String buildBox() {
        return handleDimensionsEx(() -> {
            this.styleBox();
            this.wrapWord();
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
        });
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

    private void initBorders(){
        this.hLine = "─";
        this.vLine = "│";
        this.topLeft = "┌";
        this.topRight = "┐";
        this.bottomLeft = "└";
        this.bottomRight = "┘";
    }
}
