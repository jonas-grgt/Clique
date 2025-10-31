package tables.concrete;

import tables.CellAlign;
import tables.abstracttable.WidthAwareList;
import tables.abstracttable.AbstractTable;
import utils.TableUtils;

import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.align;

public class CompactTable extends AbstractTable {

    private final StringBuilder tableBuilder;
    private static final String H_LINE = "-";
    private String vLine;

    public CompactTable(){
        super();
        this.tableBuilder = new StringBuilder();
        this.vLine = " ".repeat(this.tableConfiguration.getPadding());
    }

    @Override
    public String buildTable() {
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();
        final CellAlign cellAlign = this.tableConfiguration.getAlign();


        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);

            for (int j = 0; j < list.size(); j++) {
                final String cell = list.get(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = longest - cell.length();
                this.tableBuilder.append(align(cellAlign, sb, offset, cell, ""));

                if (j < list.size() - 1) {
                    this.tableBuilder.append(vLine);
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
                sb.append(vLine);
            }
        }

        return sb.toString();
    }
}
