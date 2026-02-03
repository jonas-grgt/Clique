package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.tables.AbstractTable.CustomizableTableHeaderBuilder;
import com.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;

public class TableFactory {

    private TableFactory(){
        throw new AssertionError();
    }

    public static TableHeaderBuilder getTable(TableType type, TableConfiguration config) {
        var table = table(type, config);
        return new TableHeaderBuilder(table);
    }

    public static TableHeaderBuilder getTable(TableType type) {
        return getTable(type, TableConfiguration.DEFAULT);
    }

    private static Table table(TableType type, TableConfiguration configuration) {
        return switch (type) {
            case DEFAULT -> new DefaultTable(configuration);
            case COMPACT -> new CompactTable(configuration);
            case BOX_DRAW -> new BoxDrawTable(configuration);
            case ROUNDED_BOX_DRAW -> new RoundedBoxDrawTable(configuration);
            case MARKDOWN -> new MarkdownTable(configuration);
        };
    }

    public static CustomizableTableHeaderBuilder getCustomizableTable(TableType type, TableConfiguration config) {
        return switch (type) {
            case DEFAULT -> {
                var table = table(type, config);
                yield new CustomizableTableHeaderBuilder( table);
            }
            default -> throw new UnsupportedOperationException("Table type: %s is not customizable".formatted(type));
        };
    }

    public static CustomizableTableHeaderBuilder getCustomizableTable(TableType type) {
        return getCustomizableTable(type, TableConfiguration.DEFAULT);
    }

}
