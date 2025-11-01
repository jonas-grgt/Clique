package tables.configuration;

import parser.token.stringparser.interfaces.AnsiStringParser;

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

    public TableConfiguration tableBorderStyle(TableBorderStyle tableBorderStyle) {
        this.tableBorderStyle = tableBorderStyle;
        return this;
    }

    public TableConfiguration padding(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding cannot be negative.");
        }
        this.padding = padding;
        return this;
    }

    public TableConfiguration alignment(CellAlign alignment) {
        this.alignment = alignment;
        return this;
    }


    public TableConfiguration parser(AnsiStringParser parser) {
        this.parser = parser;
        return this;
    }


    public TableConfiguration nullReplacement(String nullReplacement) {
        this.nullReplacement = nullReplacement;
        return this;
    }
}
