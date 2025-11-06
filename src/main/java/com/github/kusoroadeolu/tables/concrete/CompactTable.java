package com.github.kusoroadeolu.tables.concrete;

import com.github.kusoroadeolu.core.style.StyleBuilder;
import com.github.kusoroadeolu.core.misc.BorderStyle;
import com.github.kusoroadeolu.core.misc.CellAlign;
import com.github.kusoroadeolu.tables.configuration.TableConfiguration;
import com.github.kusoroadeolu.tables.structures.WidthAwareList;
import com.github.kusoroadeolu.tables.abstracttable.AbstractTable;

import static com.github.kusoroadeolu.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.core.utils.TableUtils.chooseColAlignment;

public class CompactTable extends AbstractTable {

    private final StringBuilder tableBuilder;
    private String hLine;
    private final String vLine;

    public CompactTable(TableConfiguration tableConfiguration){
        super(tableConfiguration);
        this.tableBuilder = new StringBuilder();
        this.vLine = " ".repeat(this.tableConfiguration.getPadding());
        this.hLine = "-";
    }

    public String buildTable() {
        this.styleTableBorders();
        clearStringBuilder(this.tableBuilder);
        final StringBuilder sb = new StringBuilder();
        CellAlign cellAlign;


        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);

            for (int j = 0; j < list.size(); j++) {
                cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j);
                final String originalCell = list.getOriginalText(j);
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = longest - originalCell.length();
                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
                this.tableBuilder.append(align(cellAlign, sb, offset, styledCell, ""));

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

    //Dynamically calculate the header for the table
    public String calculateHeader(StringBuilder sb){
        for (int i = 0; i < this.columns.size(); i++) {
            final WidthAwareList col = this.columns.get(i);
            sb.repeat(hLine, col.longest());

            if (i < this.columns.size() - 1) {
                sb.append(vLine);
            }
        }

        return sb.toString();
    }


    protected void styleTableBorders(){
        if(this.tableConfiguration.getBorderStyle() == null)return;
        final StyleBuilder sb = BorderStyle.styleBuilder();
        this.hLine = sb.formatReset(this.hLine, this.tableConfiguration.getBorderStyle().getHorizontalBorderStyles());
    }
}
