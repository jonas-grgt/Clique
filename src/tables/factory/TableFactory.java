package tables.factory;

import tables.Table;
import tables.concrete.BoxDrawTable;
import tables.concrete.CompactTable;
import tables.concrete.DefaultTable;
import tables.configuration.TableConfiguration;

public class TableFactory {

    public static Table getTable(TableType type, TableConfiguration config) {
        return switch (type) {
            case DEFAULT -> new DefaultTable(config);
            case COMPACT -> new CompactTable(config);
            case BOX_DRAW -> new BoxDrawTable(config);
            case null -> throw new IllegalArgumentException("TableType cannot be null");
        };
    }

    public static Table getTable(TableType type) {
        TableConfiguration configuration = TableConfiguration.builder();
        return switch (type) {
            case DEFAULT -> new DefaultTable(configuration);
            case COMPACT -> new CompactTable(configuration);
            case BOX_DRAW -> new BoxDrawTable(configuration);
            case null -> throw new IllegalArgumentException("TableType cannot be null");
        };
    }





}
