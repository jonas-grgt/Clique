package com.github.kusoroadeolu.tables.concrete;

import com.github.kusoroadeolu.core.style.StyleBuilder;
import com.github.kusoroadeolu.core.misc.CellAlign;
import com.github.kusoroadeolu.tables.abstracttable.AbstractTable;
import com.github.kusoroadeolu.core.misc.BorderStyle;
import com.github.kusoroadeolu.tables.configuration.TableConfiguration;
import com.github.kusoroadeolu.tables.structures.WidthAwareList;

import static com.github.kusoroadeolu.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.core.utils.TableUtils.chooseColAlignment;

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
        CellAlign cellAlign;


        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);
            sb.append(this.vLine);
            for (int j = 0; j < list.size(); j++) {
                cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j), originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = (longest - originalCell.length()) + this.tableConfiguration.getPadding();

                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
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
        if(this.tableConfiguration.getBorderStyle() == null)return;
        final StyleBuilder sb = BorderStyle.styleBuilder();
        this.hLine = sb.formatReset(this.hLine, this.tableConfiguration.getBorderStyle().getHorizontalBorderStyles());
        this.vLine = sb.formatReset(this.vLine, this.tableConfiguration.getBorderStyle().getVerticalBorderStyles());
    }
}
