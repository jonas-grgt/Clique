package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.config.BorderStyle;
import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.core.utils.Constants;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.tables.structures.WidthAwareList;

import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

public class BoxDrawTable extends AbstractTable{
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
        var tableBuilder = new StringBuilder();
        final StringBuilder sb = new StringBuilder();
        final String header = this.appendHeader(sb);
        clearStringBuilder(sb);
        final String footer = this.appendFooter(sb);
        clearStringBuilder(sb);
        final String headerEnd = this.drawHeaderEnd(sb);
        clearStringBuilder(sb);
        final int padding = this.tableConfiguration.getPadding();


        //Build
        tableBuilder.append(header).append(Constants.NEWLINE);
        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);
            tableBuilder.append(vLine);

            for (int j = 0; j < list.size(); j++) {
                var cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j);
                final int displayWidth = list.get(j).text().length();
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column
                final int offset = (longest - displayWidth) + padding; //Add padding to avoid cramping
                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
                tableBuilder.append(align(cellAlign, sb, offset, styledCell, vLine));

                clearStringBuilder(sb);
            }

            if(i == 0) tableBuilder.append(Constants.NEWLINE).append(headerEnd);


            tableBuilder.append(Constants.NEWLINE);
        }

        tableBuilder.append(footer);
        return tableBuilder.toString();
    }

    public String appendHeader(StringBuilder sb) {
        return drawEdges(sb, topLeft, topJoin, topRight);
    }

    public String appendFooter(StringBuilder sb) {
        return drawEdges(sb, bottomLeft, bottomJoin, bottomRight);
    }

    public String drawHeaderEnd(StringBuilder sb){
        return drawEdges(sb, leftJoin, cross, rightJoin);
    }

    private String drawEdges(StringBuilder sb, String left, String join, String right) {
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