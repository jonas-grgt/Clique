package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.config.BorderStyle;
import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.style.StyleBuilder;

import static com.github.kusoroadeolu.clique.core.utils.BoxUtils.drawBox;
import static com.github.kusoroadeolu.clique.core.utils.BoxUtils.handleDimensionsEx;

public class DefaultBox extends AbstractBox implements CustomizableBox {

    public DefaultBox(int width, int length, String content) {
        super(width, length, content);
        this.styleBox();
    }

    public DefaultBox(BoxConfiguration configuration) {
        super(configuration);
        this.styleBox();
    }

    public DefaultBox(){
        super();
        this.styleBox();
    }

    @Override
    public Box configuration(BoxConfiguration boxConfiguration) {
        throw new DeprecatedMethodException("Deprecated method. Use constructor configurations instead");
    }

    public String get() {
        return handleDimensionsEx(() -> {
            this.wrapWord();
            final StringBuilder sb = new StringBuilder();
            final BoxWrapper wrapper = new BoxWrapper(
                    this.width, this.length, this.boxConfiguration,
                    this.contentWrap, this.hLine, this.vLine,
                    this.edge, this.edge, this.edge, this.edge
            );
            drawBox(sb, wrapper);
            return sb.toString();
        });
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

    protected void styleBox(){
        if(this.boxConfiguration.getBorderStyle() != null){
            final BorderStyle borderStyle = this.boxConfiguration.getBorderStyle();
            final StyleBuilder sb = borderStyle.styleBuilder();
            final AnsiCode[] horizontalStyles = borderStyle.getHorizontalBorderStyles();
            final AnsiCode[] verticalStyles = borderStyle.getVerticalBorderStyles();
            final AnsiCode[] edgeStyles = borderStyle.getEdgeBorderStyles();

            this.hLine = sb.formatReset(this.hLine, horizontalStyles);
            this.vLine = sb.formatReset(this.vLine, verticalStyles);
            this.edge = sb.formatReset(this.edge, edgeStyles);

        }
    }

}

