package tables.concrete;

import tables.abstracttable.WidthAwareList;
import tables.abstracttable.AbstractTable;

import static utils.StringUtils.clearStringBuilder;

public class CompactTable extends AbstractTable {

    private final StringBuilder tableBuilder;
    private static final String H_LINE = "-";
    private static final String V_LINE = "  ";

    public CompactTable(){
        super();
        this.tableBuilder = new StringBuilder();
    }

    @Override
    public String buildTable() {
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);

            for (int j = 0; j < list.size(); j++) {
                final String s = list.get(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = longest - s.length();
                final String empty = sb.repeat(" ", offset).toString(); //Calculate the pos of each char, left aligned
                this.tableBuilder.append(s).append(empty);

                if (j < list.size() - 1) {
                    this.tableBuilder.append(V_LINE);
                }
                clearStringBuilder(sb);
            }

            if(i == 0){
                this.tableBuilder.append("\n").append(this.calculateHeader(sb));
                clearStringBuilder(sb);
            }

            this.tableBuilder.append("\n");
        }

        return this.tableBuilder.toString();
    }

    @Override
    public void render(){
        System.out.println(this.buildTable());
    }

    //Dynamically calculate the header for the table
    public String calculateHeader(StringBuilder sb){
        for (int i = 0; i < this.columns.size(); i++) {
            final WidthAwareList col = this.columns.get(i);
            sb.repeat(H_LINE, col.longest());

            if (i < this.columns.size() - 1) {
                sb.append(V_LINE);
            }
        }

        return sb.toString();
    }
}
