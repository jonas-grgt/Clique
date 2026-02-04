package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.BorderStyle;
import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.core.utils.Constants;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.tables.structures.WidthAwareList;

import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

public class DefaultTable extends AbstractTable implements CustomizableTable {
    private String edge;
    private String hLine;
    private String vLine;

    public DefaultTable(TableConfiguration tableConfiguration){
        super(tableConfiguration);
        this.edge = "+";
        this.hLine = "-";
        this.vLine = "|";
        this.styleTableBorders();

    }

    public String buildTable(){
        //Declarations
        final var tableBuilder = new StringBuilder();
        final var sb = new StringBuilder();
        final var headerAndFooter = this.appendHeader(sb);
        final int padding = this.tableConfiguration.getPadding();
        clearStringBuilder(sb);


        //Build
        tableBuilder.append(headerAndFooter).append(Constants.NEWLINE);

        for (final WidthAwareList list : this.rows) {
            tableBuilder.append(vLine);

            for (int j = 0; j < list.size(); j++) {
                var cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j);
                final int displayWidth = list.get(j).text().length();
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - displayWidth) + padding;

                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
                tableBuilder.append(align(cellAlign, sb, offset, styledCell, vLine));
                clearStringBuilder(sb);
            }

            tableBuilder.append(Constants.NEWLINE);
            tableBuilder.append(headerAndFooter).append(Constants.NEWLINE);
        }

        return tableBuilder.toString();
    }

    //Dynamically calculate the header and footer for the table
    private String appendHeader(StringBuilder sb){
        for (final WidthAwareList l : this.columns) {
            sb.append(edge);
            sb.repeat(hLine, l.longest() + this.tableConfiguration.getPadding());
        }
        sb.append(edge);
        return sb.toString();
    }

    public CustomizableTable customizeEdge(char edge) {
        this.edge = String.valueOf(edge);
        return this;
    }

    public CustomizableTable customizeVerticalLine(char vLine) {
        this.vLine = String.valueOf(vLine);
        return this;
    }

    public CustomizableTable customizeHorizontalLine(char hLine) {
        this.hLine = String.valueOf(hLine);
        return this;
    }


     protected void styleTableBorders(){
        if(this.tableConfiguration.getBorderStyle() == null) return;
        final BorderStyle borderStyle = this.tableConfiguration.getBorderStyle();
        final StyleBuilder sb = borderStyle.styleBuilder();
        this.hLine = sb.formatReset(this.hLine, borderStyle.getHorizontalBorderStyles());
        this.vLine = sb.formatReset(this.vLine, borderStyle.getVerticalBorderStyles());
        this.edge = sb.formatReset(this.edge, borderStyle.getEdgeBorderStyles());
    }
}
