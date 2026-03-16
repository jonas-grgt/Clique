package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.config.TableConfiguration;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.core.structures.WidthAwareList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseToCell;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.*;
import static java.util.Objects.isNull;

public abstract class AbstractTable implements Table {
    protected final List<WidthAwareList> columns; //This is used to track the max length in that column
    protected final List<WidthAwareList> rows;
    protected TableConfiguration tableConfiguration;

     AbstractTable(TableConfiguration configuration) {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tableConfiguration = configuration;
    }

     AbstractTable() {
        this(TableConfiguration.DEFAULT);
    }

    public Table addRows(Collection<String> rows) {
        return this.addRows(rows.toArray(String[]::new));
    }

    public AbstractTable addRows(String... rows) {
        Objects.requireNonNull(rows, "Rows cannot be null");
        //Get the header's size
        final int headerSize = this.rows.getFirst().size();
        final WidthAwareList rowList = new WidthAwareList();
        this.rows.add(rowList);

        for (int i = 0; i < headerSize; i++) {
            String row;
            //Pad the cells with null replacements
            if (i >= rows.length) row = this.tableConfiguration.getNullReplacement();
            else {
                row = rows[i];
                row = handleNulls(row, this.tableConfiguration.getNullReplacement());
            }

            final Cell c = parseToCell(row, this.tableConfiguration.getParser());
            rowList.add(c);
            final WidthAwareList colList = this.columns.get(i);
            colList.add(c);
        }

        return this;
    }

    public AbstractTable removeRow(int index) {
        validateHeaders(index, index);
        validateRowIndex(index, this.rows);

        this.rows.remove(index);
        for (WidthAwareList cl : this.columns) {
            cl.remove(cl.get(index));
        }

        return this;
    }

    public AbstractTable removeCell(int row, int col) {
        validateHeaders(row, col);
        this.updateCell(row, col, this.tableConfiguration.getNullReplacement());
        return this;
    }

    public AbstractTable updateCell(int row, int col, String text) {
        validateRowIndex(row, this.rows);
        validateColumnIndex(col, this.columns);

        final WidthAwareList rl = this.rows.get(row);
        final WidthAwareList cl = this.columns.get(col);
        final Cell c = parseToCell(text, this.tableConfiguration.getParser());
        rl.update(col, c);
        cl.update(row, c);
        return this;
    }

    abstract void styleTableBorders();

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        AbstractTable that = (AbstractTable) object;
        return columns.equals(that.columns) && rows.equals(that.rows) && Objects.equals(tableConfiguration, that.tableConfiguration);
    }

    public int hashCode() {
        return Objects.hash(columns, rows, tableConfiguration);
    }

    public String toString() {
        return "Table[" +
                "columns=" + columns +
                ", rows=" + rows +
                ", tableConfiguration=" + tableConfiguration +
                ']';
    }


    public static class TableHeaderBuilder {
        private final AbstractTable table;

        public TableHeaderBuilder(Table table) {
            this.table = (AbstractTable) table;
        }

        public Table addHeaders(String... headers) {
            if (isNull(headers) || headers.length == 0)
                throw new IllegalArgumentException("Headers cannot be null or empty");

            final var rowList = new WidthAwareList();
            table.rows.add(rowList);

            for (int i = 0; i < headers.length; i++) {
                String header = headers[i];
                header = handleNulls(header, table.tableConfiguration.getNullReplacement());
                final var cell = parseToCell(header, table.tableConfiguration.getParser());
                rowList.add(cell);
                final var colList = new WidthAwareList(); //To keep track of all values in this column
                colList.add(cell);
                table.columns.add(i, colList);
            }

            return table;
        }

        public Table addHeaders(Collection<String> headers) {
            return this.addHeaders(headers.toArray(String[]::new));
        }
    }

    public static class CustomizableTableHeaderBuilder {
        private final AbstractTable table;

        public CustomizableTableHeaderBuilder(Table table) {
            this.table = (AbstractTable) table;
        }

        public CustomizableTable addHeaders(String... headers) {
            var builder = new TableHeaderBuilder(table);
            builder.addHeaders(headers);
            return (CustomizableTable) table;
        }

        public CustomizableTable addHeaders(Collection<String> headers) {
            return this.addHeaders(headers.toArray(String[]::new));
        }
    }

}
