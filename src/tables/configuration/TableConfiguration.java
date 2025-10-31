package tables.configuration;

import parser.token.stringparser.interfaces.AnsiStringParser;
import tables.CellAlign;

/**
 * Configuration settings for table rendering, using a Fluent Setter pattern.
 * Note: This design makes the configuration object mutable.
 */
public class TableConfiguration {
    private int padding;
    private CellAlign align;
    private AnsiStringParser parser;
    private String nullReplacement;


    public TableConfiguration() {
        // Default initialization
        this.padding = 1;
        this.align = CellAlign.LEFT;
        this.parser = null;
        this.nullReplacement = "";
    }


    public int getPadding() {
        return padding;
    }

    public CellAlign getAlign() {
        return align;
    }

    public AnsiStringParser getParser() {
        return parser;
    }

    public String getNullReplacement() {
        return nullReplacement;
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
     * @param align The CellAlign value.
     * @return The TableConfiguration instance.
     */
    public TableConfiguration align(CellAlign align) {
        this.align = align;
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
