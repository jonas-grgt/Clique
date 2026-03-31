package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.MiscUtils.assertStyleNotNull;

/**
 * @since 1.0.0
 * */
@Stable(since = "3.2.0")
public class TableConfiguration {
    public static final TableConfiguration DEFAULT = new TableConfiguration();

    private final int padding;
    private final CellAlign alignment;
    private final AnsiStringParser parser;
    private final String nullReplacement;
    private final Map<Integer, CellAlign> columnAlignment;
    private final BorderStyle borderStyle;


    private TableConfiguration() {
        this(new TableConfigurationBuilder());
    }

    private TableConfiguration(TableConfigurationBuilder builder) {
        this.padding = builder.padding;
        this.alignment = builder.alignment;
        this.parser = builder.parser;
        this.nullReplacement = builder.nullReplacement;
        this.columnAlignment = builder.columnAlignment;
        this.borderStyle = builder.borderStyle;
    }

    public static TableConfigurationBuilder builder() {
        return new TableConfigurationBuilder();
    }

    /**
     * @deprecated As of 3.1.3, use {@link TableConfiguration#builder()} instead. This will be removed in a future release.
     * */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static TableConfigurationBuilder immutableBuilder() {
        return builder();
    }

    @InternalApi(since = "3.1.3")
    public static TableConfiguration fromBorderStyle(BorderSpec style) {
        assertStyleNotNull(style);
        return TableConfiguration.immutableBuilder().borderStyle(style).build();
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

    public static class TableConfigurationBuilder {
        private int padding = 1;
        private CellAlign alignment = CellAlign.LEFT;
        private AnsiStringParser parser = AnsiStringParser.DEFAULT;
        private String nullReplacement = "";
        private Map<Integer, CellAlign> columnAlignment = new HashMap<>();
        private BorderStyle borderStyle = null;

        public TableConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative.");
            this.padding = padding;
            return this;
        }

        public TableConfigurationBuilder alignment(CellAlign alignment) {
            Objects.requireNonNull(alignment, "Cell alignment cannot be null");
            this.alignment = alignment;
            return this;
        }

        public TableConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public TableConfigurationBuilder nullReplacement(String nullReplacement) {
            Objects.requireNonNull(nullReplacement, "Null replacement cannot be null");
            this.nullReplacement = nullReplacement;
            return this;
        }

        public TableConfigurationBuilder borderStyle(BorderSpec spec) {
            Objects.requireNonNull(spec, "Border style cannot be null");
            this.borderStyle = BorderStyle.fromSpec(spec);
            return this;
        }

        public TableConfigurationBuilder columnAlignment(Map<Integer, CellAlign> columnAlignment) {
            Objects.requireNonNull(columnAlignment, "Column alignment cannot be null");
            this.columnAlignment = columnAlignment;
            return this;
        }

        public TableConfigurationBuilder columnAlignment(int column, CellAlign alignment) {
            if (column < 0) throw new IllegalArgumentException("Column index cannot be less than 0");
            Objects.requireNonNull(alignment, "Column alignment cannot be null");
            this.columnAlignment.put(column, alignment);
            return this;
        }

        public TableConfiguration build() {
            return new TableConfiguration(this);
        }
    }
}