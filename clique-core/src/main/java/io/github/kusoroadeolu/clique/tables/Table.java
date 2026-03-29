package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.documentation.Stable;

import java.util.Collection;

/**
 * @since 3.0.0
 * */
@Stable(since = "3.1.3")
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
