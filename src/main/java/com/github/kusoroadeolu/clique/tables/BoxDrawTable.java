package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.config.BorderStyle;
import com.github.kusoroadeolu.clique.config.CellAlign;
import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.tables.structures.WidthAwareList;

import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

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
        CellAlign cellAlign;


        //Build
        this.tableBuilder.append(header).append("\n");
        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);
            this.tableBuilder.append(vLine);

            for (int j = 0; j < list.size(); j++) {
                cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j), originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - originalCell.length()) + padding; //Add one to avoid cramping
                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
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


    protected void styleTableBorders(){
        if(this.tableConfiguration.getBorderStyle() == null) return;
        final BorderStyle borderStyle = this.tableConfiguration.getBorderStyle();
        final StyleBuilder sb = borderStyle.styleBuilder();
        final AnsiCode[] horizontalStyles = borderStyle.getHorizontalBorderStyles();
        final AnsiCode[] verticalStyles = borderStyle.getVerticalBorderStyles();
        final AnsiCode[] edgeStyles = borderStyle.getEdgeBorderStyles();

        this.hLine = sb.formatReset(this.hLine, horizontalStyles);
        this.vLine = sb.formatReset(this.vLine, verticalStyles);
        this.topLeft = sb.formatReset(this.topLeft, edgeStyles);
        this.topRight = sb.formatReset(this.topRight, edgeStyles);
        this.bottomLeft = sb.formatReset(this.bottomLeft, edgeStyles);
        this.bottomRight = sb.formatReset(this.bottomRight, edgeStyles);
        this.topJoin = sb.formatReset(this.topJoin, horizontalStyles);
        this.bottomJoin = sb.formatReset(this.bottomJoin, horizontalStyles);
        this.leftJoin = sb.formatReset(this.leftJoin, verticalStyles);
        this.rightJoin = sb.formatReset(this.rightJoin, verticalStyles);
        this.cross = sb.formatReset(this.cross, horizontalStyles);

    }



}