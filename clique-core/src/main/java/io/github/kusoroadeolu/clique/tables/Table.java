package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.core.display.Bordered;

import java.util.Collection;

public interface Table extends Bordered {
    Table addRows(String... rows);

    Table addRows(Collection<String> rows);

    Table removeRow(int index);

    Table removeCell(int row, int col);

    Table updateCell(int row, int col, String text);
}
