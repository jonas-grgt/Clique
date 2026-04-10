package io.github.kusoroadeolu.clique.internal;
import io.github.kusoroadeolu.clique.components.*;
import io.github.kusoroadeolu.clique.configuration.TableConfiguration;
import io.github.kusoroadeolu.clique.configuration.TableType;
import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;

import java.util.Objects;

@InternalApi(since = "3.0.0")
public class TableFactory {

    private TableFactory() {
        throw new AssertionError();
    }

    public static PendingTable getTableBuilder(TableType type, TableConfiguration config) {
        var table = ofTable(type, config);
        return new TableHeaderBuilder(table);
    }

    public static PendingTable getTableBuilder(TableType type) {
        return getTableBuilder(type, TableConfiguration.DEFAULT);
    }

    private static Table ofTable(TableType type, TableConfiguration configuration) {
        Objects.requireNonNull(type, "Table type cannot be null");
        Objects.requireNonNull(configuration, "Table configuration cannot be null");
        return switch (type) {
            case ASCII -> new AsciiTable(configuration);
            case COMPACT -> new CompactTable(configuration);
            case BOX_DRAW -> new BoxDrawTable(configuration);
            case ROUNDED_BOX_DRAW -> new RoundedBoxDrawTable(configuration);
            case MARKDOWN -> new MarkdownTable(configuration);
        };
    }

}
