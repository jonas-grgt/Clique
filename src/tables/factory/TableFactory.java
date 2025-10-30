package tables.factory;

import tables.Table;
import tables.concrete.BoxDrawTable;
import tables.concrete.CompactTable;
import tables.concrete.DefaultTable;

public class TableFactory {

    public static Table getTable(TableType type){
        return switch (type){
            case DEFAULT -> new DefaultTable();
            case COMPACT -> new CompactTable();
            case BOX_DRAW -> new BoxDrawTable();
            case null -> throw new IllegalArgumentException();
        };
    }

}
