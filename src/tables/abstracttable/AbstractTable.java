package tables.abstracttable;

import parser.token.stringparser.interfaces.AnsiStringParser;
import tables.Table;
import tables.structures.Cell;
import tables.structures.WidthAwareList;
import tables.configuration.TableConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTable implements Table {
    protected final List<WidthAwareList> columns; //This is used to track the max length in that column
    protected final List<WidthAwareList> rows;
    private boolean headersAdded;
    protected TableConfiguration tableConfiguration;


    public AbstractTable(){
        this(TableConfiguration.builder());
    }

    public AbstractTable(TableConfiguration configuration){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tableConfiguration = configuration;
        this.headersAdded = false;
    }

    public AbstractTable configuration(TableConfiguration configuration){
        this.tableConfiguration = configuration;
        return this;
    }

    //Add the headers to the table
    public AbstractTable addHeaders(String... headers){
        if (this.headersAdded) {
            throw new IllegalStateException("Headers have already been added to this table");
        }
        final WidthAwareList rl = new WidthAwareList();
        this.rows.add(rl);

        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            header = this.handleNulls(header);
            Cell c = this.parseCell(header);
            rl.add(c);
            final WidthAwareList cl = new WidthAwareList(); //To keep track of all values in this column
            cl.add(c);
            this.columns.add(i, cl);
        }
        this.headersAdded = true;
        return this;
    }

    public AbstractTable addRows(String... rows){

        if (!this.headersAdded) {
            throw new IllegalStateException("Headers must be added before adding rows. Call addHeaders() first.");
        }

        //Get the header's size
        final int headerSize = this.rows.getFirst().size();
        final WidthAwareList rl = new WidthAwareList();
        this.rows.add(rl);

        for (int i = 0; i < headerSize; i++) {
            String row;

            //Pad the cells with null replacements
            if(i >= rows.length){
                row = this.tableConfiguration.getNullReplacement();
            }else {
                row = rows[i];
                row = this.handleNulls(row);
            }

            final Cell c = this.parseCell(row);
            rl.add(c);
            final WidthAwareList cl = this.columns.get(i);
            cl.add(c);
        }

        return this;
    }

    public AbstractTable removeRow(int index){
        this.validateHeaders(index, index);
        this.validateRowIndex(index);
        this.rows.remove(index);
        for (WidthAwareList cl : this.columns){
            cl.remove(cl.get(index));
        }

        return this;
    }

    public AbstractTable removeCell(int row, int col){
        this.validateHeaders(row, col);
        this.updateCell(row, col, this.tableConfiguration.getNullReplacement());
        return this;
    }

    public AbstractTable updateCell(int row, int col, String text){
        this.validateRowIndex(row);
        this.validateColumnIndex(col);

        final WidthAwareList rl = this.rows.get(row);
        final WidthAwareList cl = this.columns.get(col);
        final Cell c = this.parseCell(text);
        rl.update(col, c);
        cl.update(row, c);
        return this;
    }

    private Cell parseCell(String cell){
        if(this.tableConfiguration.getParser() != null){
            final AnsiStringParser p = this.tableConfiguration.getParser();
            final String styled = p.parse(cell);
            return new Cell(p.getOriginalString(), styled);
        }else {
            return new Cell(cell, cell);
        }
    }

    private String handleNulls(String val){
        if(val != null) return val;
        return this.tableConfiguration.getNullReplacement();
    }

    private void validateRowIndex(int rowIdx){
        if(rowIdx > (this.rows.size() - 1)) throw new IllegalArgumentException("Row: " + rowIdx + "does not exist");
    }

    private void validateHeaders(int idx, int col){
        if(idx == 0 || col == 0) throw new IllegalArgumentException("Cannot remove a header from a table");
    }

    private void validateColumnIndex(int colIdx){
        if(colIdx > (this.columns.size() - 1)) throw new IllegalArgumentException("Column: " + colIdx + "does not exist");
    }

    public abstract String buildTable();

    public abstract void render();


}
