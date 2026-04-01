package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import java.util.Collection;

/**
 * @since 1.0.0
 * */
@InternalApi(since = "3.2.0")
public interface Table extends Bordered {
    @Deprecated
    default Table addRows(String... rows){
        return row(rows);
    }

    @Deprecated
    default Table addRows(Collection<String> rows){
        return row(rows);
    }

    Table row(String... rows);

    Table row(Collection<String> rows);

    Table removeRow(int index);

    Table removeCell(int row, int col);

    Table updateCell(int row, int col, String text);
}
