package tables.concrete;

import core.style.StyleBuilder;
import tables.CellAlign;
import tables.configuration.TableBorderStyle;
import tables.configuration.TableConfiguration;
import tables.structures.WidthAwareList;
import tables.abstracttable.AbstractTable;

import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.align;

public class DefaultTable extends AbstractTable {

    private final StringBuilder tableBuilder;
    private String edge;
    private String hLine;
    private String vLine;

    public DefaultTable(TableConfiguration tableConfiguration){
        super(tableConfiguration);
        this.tableBuilder = new StringBuilder();
        this.edge = "+";
        this.hLine = "-";
        this.vLine = "|";
        this.styleTableBorders();
    }

    @Override
    public String buildTable(){
        //Declarations
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();
        final String headerAndFooter = this.calculateHeader(sb);
        final int padding = this.tableConfiguration.getPadding();
        final CellAlign cellAlign = this.tableConfiguration.getAlignment();
        clearStringBuilder(sb);


        //Build
        this.tableBuilder.append(headerAndFooter).append("\n");
        for (final WidthAwareList list : this.rows) {
            this.tableBuilder.append(vLine);

            for (int j = 0; j < list.size(); j++) {
                final String styledCell = list.getStyledText(j);
                final String originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - originalCell.length()) + padding;
                this.tableBuilder.append(align(cellAlign, sb, offset, styledCell, vLine));
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
            sb.append(edge);
            sb.repeat(hLine, l.longest() + this.tableConfiguration.getPadding());
        }
        sb.append(edge);
        return sb.toString();
    }

    private void styleTableBorders(){
        if(this.tableConfiguration.getTableBorderStyle() == null) return;
        final StyleBuilder sb = TableBorderStyle.styleBuilder();
        this.hLine = sb.formatReset(this.hLine, this.tableConfiguration.getTableBorderStyle().getHorizontalBorderStyles());
        this.vLine = sb.formatReset(this.vLine, this.tableConfiguration.getTableBorderStyle().getVerticalBorderStyles());
        this.edge = sb.formatReset(this.edge, this.tableConfiguration.getTableBorderStyle().getEdgeBorderStyles());
    }
}
