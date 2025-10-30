package tables.concrete;

import tables.abstracttable.WidthAwareList;
import tables.abstracttable.AbstractTable;

import static utils.StringUtils.clearStringBuilder;

public class DefaultTable extends AbstractTable {

    private final StringBuilder tableBuilder;
    private static final String B_EDGE = "+";
    private static final String H_LINE = "-";
    private static final String V_LINE = "|";

    public DefaultTable(){
        super();
        this.tableBuilder = new StringBuilder();
    }

    @Override
    public String buildTable(){
        clearStringBuilder(this.tableBuilder);

        final StringBuilder sb = new StringBuilder();
        final String headerAndFooter = this.calculateHeader(sb);

        clearStringBuilder(sb);
        this.tableBuilder.append(headerAndFooter).append("\n");

        for (final WidthAwareList list : this.rows) {
            this.tableBuilder.append(V_LINE);

            for (int j = 0; j < list.size(); j++) {
                final String s = list.get(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - s.length()) + 1; //Add one to avoid cramping
                final String empty = sb.repeat(" ", offset) + V_LINE; //TODO Extract character layout
                this.tableBuilder.append(s).append(empty);
                clearStringBuilder(sb);
            }

            this.tableBuilder.append("\n");
            this.tableBuilder.append(headerAndFooter).append("\n");
        }

        return this.tableBuilder.toString();
    }

    @Override
    public void render(){
        System.out.println(this.buildTable());
    }



    //Dynamically calculate the header and footer for the table
    public String calculateHeader(StringBuilder sb){
        for (final WidthAwareList l : this.columns) {
            sb.append(B_EDGE);
            sb.repeat(H_LINE, l.longest() + 1);
        }
        sb.append(B_EDGE);
        return sb.toString();
    }
}
