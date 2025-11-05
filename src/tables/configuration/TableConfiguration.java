package tables.configuration;

import core.clique.Clique;
import core.misc.BorderStyle;
import core.misc.CellAlign;
import parser.token.stringparser.interfaces.AnsiStringParser;

import java.util.HashMap;

public class TableConfiguration {
    private int padding;
    private CellAlign alignment;
    private AnsiStringParser parser;
    private String nullReplacement;
    private HashMap<Integer, CellAlign> columnAlignment;
    private BorderStyle borderStyle;


    private TableConfiguration() {
        // Default config
        this.padding = 1;
        this.alignment = CellAlign.LEFT;
        this.columnAlignment = new HashMap<>();
        this.parser = Clique.parser();
        this.nullReplacement = "";
        this.borderStyle = null;
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



    public BorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    public TableConfiguration borderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
        return this;
    }

    public TableConfiguration padding(int padding) {
        if (padding < 0) {
            throw new IllegalArgumentException("Padding cannot be negative.");
        }
        this.padding = padding;
        return this;
    }

    public HashMap<Integer, CellAlign> getColumnAlignment() {
        return columnAlignment;
    }

    public TableConfiguration columnAlignment(HashMap<Integer, CellAlign> columnAlignment) {
        this.columnAlignment = columnAlignment;
        return this;
    }

    public TableConfiguration columnAlignment(int column, CellAlign alignment){
        if(column < 0)throw new IllegalArgumentException("Column index cannot be less than 0");
        this.columnAlignment.put(column, alignment);
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
