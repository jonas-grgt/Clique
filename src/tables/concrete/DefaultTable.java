package tables.concrete;

import tables.CellAlign;
import tables.abstracttable.WidthAwareList;
import tables.abstracttable.AbstractTable;
import utils.TableUtils;

import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.align;

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
        //Declarations
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();
        final String headerAndFooter = this.calculateHeader(sb);
        final int padding = this.tableConfiguration.getPadding();
        final CellAlign cellAlign = this.tableConfiguration.getAlign();
        clearStringBuilder(sb);


        //Build
        this.tableBuilder.append(headerAndFooter).append("\n");
        for (final WidthAwareList list : this.rows) {
            this.tableBuilder.append(V_LINE);

            for (int j = 0; j < list.size(); j++) {
                final String cell = list.get(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - cell.length()) + padding; //Add one to avoid cramping
                this.tableBuilder.append(align(cellAlign, sb, offset, cell, V_LINE));
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
            sb.repeat(H_LINE, l.longest() + this.tableConfiguration.getPadding());
        }
        sb.append(B_EDGE);
        return sb.toString();
    }
}
