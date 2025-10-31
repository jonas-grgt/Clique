package tables;

import tables.abstracttable.AbstractTable;

public interface Table {
    Table addHeaders(String... headers);
    Table addRows(String... rows);
    Table removeRow(int index);

    AbstractTable removeCell(int row, int col);

    AbstractTable updateCell(int row, int col, String text);

    String buildTable();
    void render();
}
