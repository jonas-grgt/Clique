package tables.interfaces;

public interface Table {
    Table addHeaders(String... headers);

    Table addRows(String... rows);
    Table removeRow(int index);
    Table removeCell(int row, int col);
    Table updateCell(int row, int col, String text);

    String buildTable();
    void render();
}
