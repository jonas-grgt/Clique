package tables.configuration;

import parser.token.stringparser.interfaces.AnsiStringParser;
import tables.CellAlign;

public class TableConfiguration {
    private int padding;
    private CellAlign alignment;
    private AnsiStringParser parser;
    private String nullReplacement;
    private TableBorderStyle tableBorderStyle;


    private TableConfiguration() {
        // Default config
        this.padding = 1;
        this.alignment = CellAlign.LEFT;
        this.parser = null;
        this.nullReplacement = "";
        this.tableBorderStyle = null;
    }

    public static TableConfiguration builder(){
        return new TableConfiguration();
    }


    public int getPadding() {
        return this.padding;
    }

    public CellAlign getAlignment() {
        return this.alignment;
    }

    public AnsiStringParser getParser() {
        return this.parser;
    }

    public String getNullReplacement() {
        return this.nullReplacement;
    }



    public TableBorderStyle getTableBorderStyle() {
        return this.tableBorderStyle;
    }

    /**
     * Sets the default table style. Default is null.
     * @param tableBorderStyle The table style value.
     * @return The TableConfiguration instance.
     */
    public TableConfiguration tableBorderStyle(TableBorderStyle tableBorderStyle) {
        this.tableBorderStyle = tableBorderStyle;
        return this;
    }

    /**
     * Sets the cell padding for the table. Default is 1.
     * @param padding The padding value (minimum 0).
     * @return The TableConfiguration instance.
     */
    public TableConfiguration padding(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding cannot be negative.");
        }
        this.padding = padding;
        return this;
    }

    /**
     * Sets the default cell alignment. Default is CellAlign.LEFT.
     * @param alignment The CellAlign value.
     * @return The TableConfiguration instance.
     */
    public TableConfiguration alignment(CellAlign alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * Sets the ANSI string parser for handling colored/styled text. Default is null.
     * @param parser The AnsiStringParser instance.
     * @return The TableConfiguration instance.
     */
    public TableConfiguration parser(AnsiStringParser parser) {
        this.parser = parser;
        return this;
    }

    /**
     * Sets the replacement string for null values. Default is an empty string "".
     * @param nullReplacement The replacement string.
     * @return The TableConfiguration instance .
     */
    public TableConfiguration nullReplacement(String nullReplacement) {
        this.nullReplacement = nullReplacement;
        return this;
    }
}
