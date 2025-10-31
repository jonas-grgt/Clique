package tables.configuration;

import core.ansi.interfaces.AnsiCode;
import core.clique.Clique;
import core.style.StyleBuilder;

/**
 * A class for styling table borders
 * */
public class TableBorderStyle {
    private AnsiCode[] verticalStyle;
    private AnsiCode[] horizontalStyle;
    private AnsiCode[] cornerStyle;
    private static final StyleBuilder styleBuilder = Clique.styleBuilder();

    public static TableBorderStyle builder(){
        return new TableBorderStyle();
    }

    public static StyleBuilder styleBuilder(){
        return styleBuilder;
    }

    public AnsiCode[] getHorizontalBorderStyles() {
        return horizontalStyle;
    }

    public TableBorderStyle horizontalBorderStyles(AnsiCode... horizontalStyles) {
        this.horizontalStyle = horizontalStyles;
        return this;
    }

    public AnsiCode[] getEdgeBorderStyles() {
        return cornerStyle;
    }

    public TableBorderStyle edgeBorderStyles(AnsiCode... edgeStyles) {
        this.cornerStyle = edgeStyles;
        return this;
    }

    public AnsiCode[] getVerticalBorderStyles() {
        return verticalStyle;
    }

    public TableBorderStyle verticalBorderStyles(AnsiCode... verticalStyles) {
        this.verticalStyle = verticalStyles;
        return this;
    }
}
