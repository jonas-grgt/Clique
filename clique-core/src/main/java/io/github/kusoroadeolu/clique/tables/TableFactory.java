package io.github.kusoroadeolu.clique.tables;

import io.github.kusoroadeolu.clique.config.TableConfiguration;
import io.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;

import java.util.Objects;

public class TableFactory {

    private TableFactory(){
        throw new AssertionError();
    }

    public static TableHeaderBuilder getTableBuilder(TableType type, TableConfiguration config) {
        var table = ofTable(type, config);
        return new TableHeaderBuilder(table);
    }

    public static TableHeaderBuilder getTableBuilder(TableType type) {
        return getTableBuilder(type, TableConfiguration.DEFAULT);
    }

    public static CustomizableTableHeaderBuilder getCustomizableTableBuilder(TableType type, TableConfiguration config) {
        return switch (type) {
            case DEFAULT -> {
                var table = ofTable(type, config);
                yield new CustomizableTableHeaderBuilder(table);
            }
            default -> throw new UnsupportedOperationException("Table type: %s is not customizable".formatted(type));
        };
    }

    public static CustomizableTableHeaderBuilder getCustomizableTableBuilder(TableType type) {
        return getCustomizableTableBuilder(type, TableConfiguration.DEFAULT);
    }

    private static Table ofTable(TableType type, TableConfiguration configuration) {
        Objects.requireNonNull(type, "Table type cannot be null");
        Objects.requireNonNull(configuration, "Table configuration cannot be null");
        return switch (type) {
            case DEFAULT -> new DefaultTable(configuration);
            case COMPACT -> new CompactTable(configuration);
            case BOX_DRAW -> new BoxDrawTable(configuration);
            case ROUNDED_BOX_DRAW -> new RoundedBoxDrawTable(configuration);
            case MARKDOWN -> new MarkdownTable(configuration);
        };
    }

}
