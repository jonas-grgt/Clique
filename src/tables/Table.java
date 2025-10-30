package tables;

public interface Table {
    Table addHeaders(String... headers);
    Table addRows(String... rows);
}
