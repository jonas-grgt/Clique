package tables.abstracttable;

import parser.token.stringparser.interfaces.AnsiStringParser;
import tables.CellAlign;
import tables.Table;
import tables.configuration.TableConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTable implements Table {
    protected final List<WidthAwareList> columns; //This is used to track the max length in that column
    protected final List<WidthAwareList> rows;
    protected TableConfiguration tableConfiguration;


    public AbstractTable(){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tableConfiguration = new TableConfiguration();
    }

    public AbstractTable(TableConfiguration configuration){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tableConfiguration = configuration;
    }

    //Add the headers.
    public AbstractTable addHeaders(String... headers){
        final WidthAwareList rl = new WidthAwareList(this.tableConfiguration.getNullReplacement());
        this.rows.add(rl);

        for (int i = 0; i < headers.length; i++) {
            final String header = headers[i];
            rl.add(header);

            final WidthAwareList cl = new WidthAwareList(this.tableConfiguration.getNullReplacement()); //To keep track of all values in this column
            cl.add(header);
            this.columns.add(i, cl);
        }

        return this;

    }

    public AbstractTable addRows(String... rows){
        //Get the header's size
        final int headerSize = this.rows
                .getFirst()
                .size();
        final WidthAwareList rl = new WidthAwareList(this.tableConfiguration.getNullReplacement());
        this.rows.add(rl);


        for (int i = 0; i < headerSize; i++) {
            String val;

            if(i >= rows.length){
                val = "";
            }else {
                val = rows[i];
            }

            rl.add(val);
            final WidthAwareList cl = this.columns.get(i);
            cl.add(val);
        }

        return this;
    }

    public abstract String buildTable();

    public abstract void render();


}
