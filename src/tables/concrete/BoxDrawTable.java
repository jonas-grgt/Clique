package tables.concrete;

import tables.CellAlign;
import tables.abstracttable.WidthAwareList;
import tables.abstracttable.AbstractTable;

import static utils.StringUtils.clearStringBuilder;
import static utils.TableUtils.align;

public class BoxDrawTable extends AbstractTable{
    private final StringBuilder tableBuilder;
    private static final String H_LINE = "─";
    private static final String V_LINE = "│";

    private static final String TOP_LEFT = "┌";
    private static final String TOP_RIGHT = "┐";
    private static final String BOTTOM_LEFT = "└";
    private static final String BOTTOM_RIGHT = "┘";

    private static final String TOP_JOIN = "┬";
    private static final String BOTTOM_JOIN = "┴";
    private static final String LEFT_JOIN = "├";
    private static final String RIGHT_JOIN = "┤";

    private static final String CROSS = "┼";

    public BoxDrawTable(){
        super();
        this.tableBuilder = new StringBuilder();
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
        final CellAlign cellAlign = this.tableConfiguration.getAlign();


        //Build
        this.tableBuilder.append(header).append("\n");
        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);
            //noinspection DuplicatedCode
            this.tableBuilder.append(V_LINE);

            for (int j = 0; j < list.size(); j++) {
                final String cell = list.get(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = longest - cell.length() + padding; //Add one to avoid cramping
                this.tableBuilder.append(align(CellAlign.LEFT, sb, offset, cell, V_LINE));
                clearStringBuilder(sb);
            }

            if(i == 0){
                this.tableBuilder.append("\n").append(headerEnd);
            }

            this.tableBuilder.append("\n");
        }

        this.tableBuilder.append(footer).append("\n");
        return this.tableBuilder.toString();
    }

    public String calculateHeader(StringBuilder sb) {
        return calculateEdges(sb, TOP_LEFT, TOP_JOIN, TOP_RIGHT);
    }

    public String calculateFooter(StringBuilder sb) {
        return calculateEdges(sb, BOTTOM_LEFT, BOTTOM_JOIN, BOTTOM_RIGHT);
    }

    public String calculateHeaderEnd(StringBuilder sb){
        return calculateEdges(sb, LEFT_JOIN, CROSS, RIGHT_JOIN);
    }

    private String calculateEdges(StringBuilder sb, String left, String join, String right) {
        sb.append(left);
        for (int i = 0; i < this.columns.size(); i++){
            final WidthAwareList l = this.columns.get(i);
            sb.repeat(H_LINE, l.longest() + this.tableConfiguration.getPadding());

            if(i < this.columns.size() - 1){
                sb.append(join);
            }
        }

        sb.append(right);
        return sb.toString();
    }

    @Override
    public void render() {
        System.out.println(this.buildTable());
    }


}
