package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;

public class TableConfiguration {
    private final int padding;
    private final CellAlign alignment;
    private final AnsiStringParser parser;
    private final String nullReplacement;
    private final Map<Integer, CellAlign> columnAlignment;
    private final BorderStyle borderStyle;
    public static final TableConfiguration DEFAULT = new TableConfiguration();

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
        this.nullReplacement = EMPTY;
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
    public Map<Integer, CellAlign> getColumnAlignment() {
        return new HashMap<>(this.columnAlignment);
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        TableConfiguration that = (TableConfiguration) object;
        return padding == that.padding && alignment == that.alignment && parser.equals(that.parser) && nullReplacement.equals(that.nullReplacement) && columnAlignment.equals(that.columnAlignment) && Objects.equals(borderStyle, that.borderStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, alignment, parser, nullReplacement, columnAlignment, borderStyle);
    }

    @Override
    public String toString() {
        return "TableConfiguration[" +
                "padding=" + padding +
                ", alignment=" + alignment +
                ", parser=" + parser +
                ", nullReplacement='" + nullReplacement + '\'' +
                ", columnAlignment=" + columnAlignment +
                ", borderStyle=" + borderStyle +
                ']';
    }

    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration padding(int padding) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }


    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration alignment(CellAlign alignment) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }


    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration parser(AnsiStringParser parser) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }


    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration nullReplacement(String nullReplacement) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
    }


    @Deprecated(since = "1.2.1", forRemoval = true)
    public TableConfiguration borderStyle(BorderStyle borderStyle) {
        throw new DeprecatedMethodException("Deprecated method. Use the immutable builder");
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