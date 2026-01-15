package com.github.kusoroadeolu.clique.tables;

import java.util.Collection;

public interface Table {
    Table addHeaders(String... headers);
    Table addHeaders(Collection<String> headers);

    Table addRows(String... rows);
    Table addRows(Collection<String> rows);

    Table removeRow(int index);
    Table removeCell(int row, int col);
    Table updateCell(int row, int col, String text);

    String buildTable();
    void render();
}
