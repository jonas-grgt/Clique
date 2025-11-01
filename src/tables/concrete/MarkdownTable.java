package tables.concrete;

import core.style.StyleBuilder;
import tables.configuration.CellAlign;
import tables.abstracttable.AbstractTable;
import tables.configuration.TableBorderStyle;
import tables.configuration.TableConfiguration;
import tables.structures.WidthAwareList;

import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.align;

public class MarkdownTable extends AbstractTable {
    private final StringBuilder tableBuilder;
    private String hLine;
    private String vLine;

    public MarkdownTable(TableConfiguration tableConfiguration){
        super(tableConfiguration);
        this.tableBuilder = new StringBuilder();
        this.vLine = "|";
        this.hLine = "-";

    }

    public String buildTable() {
        this.styleTableBorders();
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();
        final CellAlign cellAlign = this.tableConfiguration.getAlignment();


        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);
            sb.append(this.vLine);
            for (int j = 0; j < list.size(); j++) {
                final String styledCell = list.getStyledText(j);
                final String originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - originalCell.length()) + this.tableConfiguration.getPadding();
                this.tableBuilder.append(align(cellAlign, sb, offset, styledCell, this.vLine));
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

    //Dynamically calculate the header for the table
    public String calculateHeader(StringBuilder sb){
        sb.append(this.vLine);
        for (final WidthAwareList col : this.columns) {
            sb.repeat(this.hLine, col.longest() + this.tableConfiguration.getPadding());
            sb.append(this.vLine);
        }

        return sb.toString();
    }


    protected void styleTableBorders(){
        if(this.tableConfiguration.getTableBorderStyle() == null)return;
        final StyleBuilder sb = TableBorderStyle.styleBuilder();
        this.hLine = sb.formatReset(this.hLine, this.tableConfiguration.getTableBorderStyle().getHorizontalBorderStyles());
        this.vLine = sb.formatReset(this.vLine, this.tableConfiguration.getTableBorderStyle().getVerticalBorderStyles());
    }
}
