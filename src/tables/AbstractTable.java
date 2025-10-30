package tables;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTable{
    protected final List<LengthAwareList> columns; //This is used to track the max length in that column
    protected final List<LengthAwareList> rows;

    public AbstractTable(){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    //Add the headers.
    public AbstractTable addHeaders(String... headers){
        LengthAwareList rl = new LengthAwareList();
        this.rows.add(rl);

        for (int i = 0; i < headers.length; i++) {
            final String header = headers[i];
            rl.add(header);

            final LengthAwareList cl = new LengthAwareList(); //To keep track of all values in this column
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
        final LengthAwareList rl = new LengthAwareList();
        this.rows.add(rl);


        for (int i = 0; i < headerSize; i++) {
            String val;

            if(i >= rows.length){
                val = " ";
            }else {
                val = rows[i];
            }

            rl.add(val);
            final LengthAwareList cl = this.columns.get(i);
            cl.add(val);
        }

        return this;
    }

    public abstract String buildTable();

    public abstract void render();

}
