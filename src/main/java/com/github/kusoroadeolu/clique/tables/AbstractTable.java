package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.core.display.Renderable;
import com.github.kusoroadeolu.clique.tables.structures.Cell;
import com.github.kusoroadeolu.clique.tables.structures.WidthAwareList;

import java.util.ArrayList;
import java.util.List;

import static com.github.kusoroadeolu.clique.core.utils.StringUtils.parseCell;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.*;

public abstract class AbstractTable implements Table, Renderable {
    protected final List<WidthAwareList> columns; //This is used to track the max length in that column
    protected final List<WidthAwareList> rows;
    private boolean headersAdded;
    protected TableConfiguration tableConfiguration;

    public AbstractTable(TableConfiguration configuration){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tableConfiguration = configuration;
        this.headersAdded = false;
    }

    public AbstractTable(){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tableConfiguration = TableConfiguration.immutableBuilder().build();
        this.headersAdded = false;
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
            header = handleNulls(header, this.tableConfiguration.getNullReplacement());
            final Cell c = parseCell(header, this.tableConfiguration.getParser());
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
                row = handleNulls(row, this.tableConfiguration.getNullReplacement());
            }

            final Cell c = parseCell(row, this.tableConfiguration.getParser());
            rl.add(c);
            final WidthAwareList cl = this.columns.get(i);
            cl.add(c);
        }

        return this;
    }

    public AbstractTable removeRow(int index){
        validateHeaders(index, index);
        validateRowIndex(index, this.rows);
        this.rows.remove(index);
        for (WidthAwareList cl : this.columns){
            cl.remove(cl.get(index));
        }

        return this;
    }

    public AbstractTable removeCell(int row, int col){
        validateHeaders(row, col);
        this.updateCell(row, col, this.tableConfiguration.getNullReplacement());
        return this;
    }

    public AbstractTable updateCell(int row, int col, String text){
        validateRowIndex(row, this.rows);
        validateColumnIndex(col, this.columns);

        final WidthAwareList rl = this.rows.get(row);
        final WidthAwareList cl = this.columns.get(col);
        final Cell c = parseCell(text, this.tableConfiguration.getParser());
        rl.update(col, c);
        cl.update(row, c);
        return this;
    }


    public void render() {
        System.out.println(this.buildTable());
    }

    protected abstract void styleTableBorders();

    public abstract String buildTable();


}
