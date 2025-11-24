package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.BorderStyle;
import com.github.kusoroadeolu.clique.config.CellAlign;
import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.tables.structures.WidthAwareList;

import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

public class DefaultTable extends AbstractTable implements CustomizableTable {

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

    public String buildTable(){
        //Declarations
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();
        final String headerAndFooter = this.calculateHeader(sb);
        final int padding = this.tableConfiguration.getPadding();
        CellAlign cellAlign;
        clearStringBuilder(sb);


        //Build
        this.tableBuilder.append(headerAndFooter).append("\n");

        for (final WidthAwareList list : this.rows) {
            this.tableBuilder.append(vLine);

            for (int j = 0; j < list.size(); j++) {
                cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j);
                final String originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - originalCell.length()) + padding;

                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
                this.tableBuilder.append(align(cellAlign, sb, offset, styledCell, vLine));
                clearStringBuilder(sb);
            }

            this.tableBuilder.append("\n");
            this.tableBuilder.append(headerAndFooter).append("\n");
        }

        return this.tableBuilder.toString();
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
