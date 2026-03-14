package io.github.kusoroadeolu.clique.boxes;


import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.drawBox;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.handleDimensionsEx;

public class ClassicBox extends AbstractBox {

    protected String topLeft;
    protected String topRight;
    protected String bottomLeft;
    protected String bottomRight;

    public ClassicBox() {
        super();
        this.initClassicBorders();
        this.styleBox();
    }

    public ClassicBox(BoxConfiguration configuration) {
        super(configuration);
        this.initClassicBorders();
        this.styleBox();

    }

    public ClassicBox(int width, int length, String content) {
        super(width, length, content);
        this.initClassicBorders();
    }

    public String get() {
        return handleDimensionsEx(() -> {
            this.wrapWord();
            final StringBuilder sb = new StringBuilder();
            final BoxWrapper wrapper = new BoxWrapper(
                    this.width,
                    this.height,
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

    protected void styleBox() {
        if (this.boxConfiguration.getBorderStyle() != null) {
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

    private void initClassicBorders() {
        this.hLine = "─";
        this.vLine = "│";
        this.topLeft = "┌";
        this.topRight = "┐";
        this.bottomLeft = "└";
        this.bottomRight = "┘";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        ClassicBox that = (ClassicBox) object;
        return topLeft.equals(that.topLeft) && topRight.equals(that.topRight) && bottomLeft.equals(that.bottomLeft) && bottomRight.equals(that.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, topRight, bottomLeft, bottomRight, super.hashCode());
    }

    @Override
    public String toString() {
        return "ClassicBox[" +
                "topLeft='" + topLeft + '\'' +
                ", topRight='" + topRight + '\'' +
                ", bottomLeft='" + bottomLeft + '\'' +
                ", bottomRight='" + bottomRight + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", boxContent=" + boxContent +
                ", contentWrap=" + contentWrap +
                ", vLine='" + vLine + '\'' +
                ", hLine='" + hLine + '\'' +
                ", edge='" + edge + '\'' +
                ", configuration=" + boxConfiguration +
                ']';
    }
}
