package tables.concrete;

import core.ansi.interfaces.AnsiCode;
import core.style.StyleBuilder;
import tables.CellAlign;
import tables.configuration.TableBorderStyle;
import tables.configuration.TableConfiguration;
import tables.structures.WidthAwareList;
import tables.abstracttable.AbstractTable;

import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.align;

public class BoxDrawTable extends AbstractTable{
    private final StringBuilder tableBuilder;
    private String hLine;
    private String vLine;

    protected String topLeft;
    protected String topRight;
    protected String bottomLeft;
    protected String bottomRight;

    private String topJoin;
    private String bottomJoin;
    private String leftJoin;
    private String rightJoin;

    private String cross;

    public BoxDrawTable(TableConfiguration tableConfiguration){
        super(tableConfiguration);
        this.tableBuilder = new StringBuilder();

        this.hLine = "─";
        this.vLine = "│";
        this.topLeft = "┌";
        this.topRight = "┐";
        this.bottomLeft = "└";
        this.bottomRight = "┘";
        this.topJoin = "┬";
        this.bottomJoin = "┴";
        this.leftJoin = "├";
        this.rightJoin = "┤";
        this.cross = "┼";
        this.styleTableBorders();
    }

    @Override
    public String buildTable() {
        //Declarations
        final StringBuilder sb = new StringBuilder();
        clearStringBuilder(this.tableBuilder);
        final String header = this.calculateHeader(sb);
        clearStringBuilder(sb);
        final String footer = this.calculateFooter(sb);
        clearStringBuilder(sb);
        final String headerEnd = this.calculateHeaderEnd(sb);
        clearStringBuilder(sb);
        final int padding = this.tableConfiguration.getPadding();
        final CellAlign cellAlign = this.tableConfiguration.getAlignment();


        //Build
        this.tableBuilder.append(header).append("\n");
        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);
            //noinspection DuplicatedCode
            this.tableBuilder.append(vLine);

            for (int j = 0; j < list.size(); j++) {
                final String styledCell = list.getStyledText(j);
                final String originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - originalCell.length()) + padding; //Add one to avoid cramping
                this.tableBuilder.append(align(cellAlign, sb, offset, styledCell, vLine));
                clearStringBuilder(sb);
            }

            if(i == 0){
                this.tableBuilder.append("\n").append(headerEnd);
            }

            this.tableBuilder.append("\n");
        }

        this.tableBuilder.append(footer);
        return this.tableBuilder.toString();
    }

    public String calculateHeader(StringBuilder sb) {
        return calculateEdges(sb, topLeft, topJoin, topRight);
    }

    public String calculateFooter(StringBuilder sb) {
        return calculateEdges(sb, bottomLeft, bottomJoin, bottomRight);
    }

    public String calculateHeaderEnd(StringBuilder sb){
        return calculateEdges(sb, leftJoin, cross, rightJoin);
    }

    private String calculateEdges(StringBuilder sb, String left, String join, String right) {
        sb.append(left);
        for (int i = 0; i < this.columns.size(); i++){
            final WidthAwareList l = this.columns.get(i);
            sb.repeat(hLine, l.longest() + this.tableConfiguration.getPadding());

            if(i < this.columns.size() - 1){
                sb.append(join);
            }
        }

        sb.append(right);
        return sb.toString();
    }


    public void render() {
        System.out.println(this.buildTable());
    }


    protected void styleTableBorders(){
        if(this.tableConfiguration.getTableBorderStyle() == null) return;
        final StyleBuilder sb = TableBorderStyle.styleBuilder();
        final AnsiCode[] horizontalStyles = this.tableConfiguration.getTableBorderStyle().getHorizontalBorderStyles();
        final AnsiCode[] verticalStyles = this.tableConfiguration.getTableBorderStyle().getVerticalBorderStyles();
        final AnsiCode[] cornerStyles = this.tableConfiguration.getTableBorderStyle().getEdgeBorderStyles();

        this.hLine = sb.formatReset(this.hLine, horizontalStyles);
        this.vLine = sb.formatReset(this.vLine, verticalStyles);
        this.topLeft = sb.formatReset(this.topLeft, cornerStyles);
        this.topRight = sb.formatReset(this.topRight, cornerStyles);
        this.bottomLeft = sb.formatReset(this.bottomLeft, cornerStyles);
        this.bottomRight = sb.formatReset(this.bottomRight, cornerStyles);
        this.topJoin = sb.formatReset(this.topJoin, horizontalStyles);
        this.bottomJoin = sb.formatReset(this.bottomJoin, horizontalStyles);
        this.leftJoin = sb.formatReset(this.leftJoin, verticalStyles);
        this.rightJoin = sb.formatReset(this.rightJoin, verticalStyles);
        this.cross = sb.formatReset(this.cross, horizontalStyles);

    }



}