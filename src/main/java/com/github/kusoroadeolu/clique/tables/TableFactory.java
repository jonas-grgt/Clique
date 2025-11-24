package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.TableConfiguration;

public class TableFactory {

    private TableFactory(){

    }

    public static Table getTable(TableType type, TableConfiguration config) {
        return table(type, config);
    }

    public static Table getTable(TableType type) {
        TableConfiguration configuration = TableConfiguration.immutableBuilder().build();
        return table(type, configuration);
    }

    private static Table table(TableType type, TableConfiguration configuration) {
        return switch (type) {
            case DEFAULT -> new DefaultTable(configuration);
            case COMPACT -> new CompactTable(configuration);
            case BOX_DRAW -> new BoxDrawTable(configuration);
            case ROUNDED_BOX_DRAW -> new RoundedBoxDrawTable(configuration);
            case MARKDOWN -> new MarkdownTable(configuration);
            case null -> throw new IllegalArgumentException("TableType cannot be null");
        };
    }

    public static CustomizableTable getCustomizableTable(TableType type, TableConfiguration config) {
        return switch (type) {
            case DEFAULT -> (CustomizableTable) table(type, config);
            default -> throw new UnsupportedOperationException(type + " is not customizable");
        };
    }

    public static CustomizableTable getCustomizableTable(TableType type) {
        return getCustomizableTable(type, TableConfiguration.immutableBuilder().build());
    }





}
