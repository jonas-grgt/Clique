package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.HashMap;
import java.util.Map;

public class TableConfiguration {
    private final int padding;
    private final CellAlign alignment;
    private final AnsiStringParser parser;
    private final String nullReplacement;
    private final Map<Integer, CellAlign> columnAlignment;
    private final BorderStyle borderStyle;

    @Deprecated(since = "1.2.1", forRemoval = true)
    public static TableConfiguration builder(){
        return new TableConfiguration();
    }

    private TableConfiguration() {
        // Default config
        this.padding = 1;
        this.alignment = CellAlign.LEFT;
        this.columnAlignment = new HashMap<>();
        this.parser = Clique.parser();
        this.nullReplacement = "";
        this.borderStyle = null;
    }

    private TableConfiguration(TableConfigurationBuilder builder) {
        this.padding = builder.padding;
        this.alignment = builder.alignment;
        this.parser = builder.parser;
        this.nullReplacement = builder.nullReplacement;
        this.columnAlignment = builder.columnAlignment;
        this.borderStyle = builder.borderStyle;
    }

    public static TableConfigurationBuilder immutableBuilder(){
        return new TableConfigurationBuilder();
    }

    public int getPadding() {
        return this.padding;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration padding(int padding) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public CellAlign getAlignment() {
        return this.alignment;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration alignment(CellAlign alignment) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public AnsiStringParser getParser() {
        return this.parser;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration parser(AnsiStringParser parser) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public String getNullReplacement() {
        return this.nullReplacement;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration nullReplacement(String nullReplacement) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public BorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration borderStyle(BorderStyle borderStyle) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public Map<Integer, CellAlign> getColumnAlignment() {
        return this.columnAlignment;
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration columnAlignment(Map<Integer, CellAlign> columnAlignment) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration columnAlignment(int column, CellAlign alignment){
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }

    public static class TableConfigurationBuilder {
        private int padding = 1;
        private CellAlign alignment = CellAlign.LEFT;
        private AnsiStringParser parser = Clique.parser();
        private String nullReplacement = "";
        private Map<Integer, CellAlign> columnAlignment = new HashMap<>();
        private BorderStyle borderStyle = null;

        public TableConfigurationBuilder padding(int padding) {
            if (padding < 0) {
                throw new IllegalArgumentException("Padding cannot be negative.");
            }
            this.padding = padding;
            return this;
        }

        public TableConfigurationBuilder alignment(CellAlign alignment) {
            this.alignment = alignment;
            return this;
        }

        public TableConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public TableConfigurationBuilder nullReplacement(String nullReplacement) {
            this.nullReplacement = nullReplacement;
            return this;
        }

        public TableConfigurationBuilder borderStyle(BorderStyle borderStyle) {
            this.borderStyle = borderStyle;
            return this;
        }

        public TableConfigurationBuilder columnAlignment(Map<Integer, CellAlign> columnAlignment) {
            this.columnAlignment = columnAlignment;
            return this;
        }

        public TableConfigurationBuilder columnAlignment(int column, CellAlign alignment){
            if(column < 0) throw new IllegalArgumentException("Column index cannot be less than 0");
            this.columnAlignment.put(column, alignment);
            return this;
        }

        public TableConfiguration build() {
            return new TableConfiguration(this);
        }
    }
}