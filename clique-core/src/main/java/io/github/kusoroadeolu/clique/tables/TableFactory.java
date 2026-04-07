package io.github.kusoroadeolu.clique.tables;

import io.github.kusoroadeolu.clique.config.TableConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.tables.AbstractTable.TableHeaderBuilder;

import java.util.Objects;

@InternalApi(since = "3.2.0")
public class TableFactory {

    private TableFactory() {
        throw new AssertionError();
    }

    public static TableHeaderBuilder getTableBuilder(TableType type, TableConfiguration config) {
        var table = ofTable(type, config);
        return new TableHeaderBuilder(table);
    }

    public static TableHeaderBuilder getTableBuilder(TableType type) {
        return getTableBuilder(type, TableConfiguration.DEFAULT);
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
