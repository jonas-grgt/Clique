package tables.factory;

import tables.Table;
import tables.concrete.BoxDrawTable;
import tables.concrete.CompactTable;
import tables.concrete.DefaultTable;
import tables.concrete.RoundedBoxDrawTable;
import tables.configuration.TableConfiguration;

public class TableFactory {

    public static Table getTable(TableType type, TableConfiguration config) {
        return table(type, config);
    }

    public static Table getTable(TableType type) {
        TableConfiguration configuration = TableConfiguration.builder();
        return table(type, configuration);
    }

    private static Table table(TableType type, TableConfiguration configuration) {
        return switch (type) {
            case DEFAULT -> new DefaultTable(configuration);
            case COMPACT -> new CompactTable(configuration);
            case BOX_DRAW -> new BoxDrawTable(configuration);
            case ROUNDED_BOX_DRAW -> new RoundedBoxDrawTable(configuration);
            case null -> throw new IllegalArgumentException("TableType cannot be null");
        };
    }


}
