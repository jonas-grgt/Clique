package tables;

import java.util.ArrayList;
import java.util.List;

import static utils.StringUtils.clearStringBuilder;

public class Table {
    private final List<LengthAwareList> columns; //This is used to track the max length in that column
    private final List<LengthAwareList> rows;
    private static final String B_EDGE = "+";
    private static final String H_LINE = "-";
    private static final String V_LINE = "|";

    public Table(){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    //Add the headers. //TODO Create a custom builder for this
    public Table addHeaders(String... headers){
        LengthAwareList rl = new LengthAwareList();
        this.rows.add(rl);

        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            rl.add(header);

            final LengthAwareList cl = new LengthAwareList(); //To keep track of all values in this column
            cl.add(header);
            this.columns.add(i, cl);
        }

        return this;

    }

    public Table addRows(String... rows){
        //Get the header's size
        final int headerSize = this.rows
                .getFirst()
                .size();
        final LengthAwareList l = new LengthAwareList();
        this.rows.add(l);



        for (int i = 0; i < headerSize; i++) {
            String row;

            if(i >= rows.length){
                row = " ";
            }else {
                row = rows[i];
            }

            l.add(row);
            final LengthAwareList cl = this.columns.get(i);
            cl.add(row);
        }

        return this;
    }

    public void render(){
        final StringBuilder sb = new StringBuilder();
        final int headerLen = this.calculateHeaderLength();

        final String headerAndFooter = B_EDGE +
                sb.repeat(H_LINE, headerLen) +
                B_EDGE;

        clearStringBuilder(sb);
        System.out.println(headerAndFooter);

        for (final LengthAwareList list : this.rows) {
            System.out.print(V_LINE);

            for (int j = 0; j < list.size(); j++) {
                final String s = list.get(j);
                final LengthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest int in each column

                final int offset = longest - s.length();
                final String empty = sb.repeat(" ", offset) + V_LINE;
                System.out.print(s + empty);
                clearStringBuilder(sb);
            }

            System.out.println();
            System.out.println(headerAndFooter);
        }
    }

    public int calculateHeaderLength(){
        return this.columns
                .stream()
                .mapToInt(LengthAwareList::longest)
                .sum() + (this.columns.size() - 1); //Add the column size decremented to make up for the vertical borders
    }



}
