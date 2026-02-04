package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.config.TableConfiguration;
import com.github.kusoroadeolu.clique.core.utils.Constants;
import com.github.kusoroadeolu.clique.style.StyleBuilder;
import com.github.kusoroadeolu.clique.tables.structures.WidthAwareList;

import static com.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static com.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

public class CompactTable extends AbstractTable {
    private String hLine;
    private final String vLine;

    public CompactTable(TableConfiguration tableConfiguration){
        super(tableConfiguration);
        this.vLine = Constants.BLANK.repeat(this.tableConfiguration.getPadding());
        this.hLine = "-";
        this.styleTableBorders();
    }

    public String buildTable() {
        final var tableBuilder = new StringBuilder();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);

            for (int j = 0; j < list.size(); j++) {
                var cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j);
                final int displayWidth = list.get(j).text().length();
                final WidthAwareList cl = this.columns.get(j);
                final int longest = cl.longest(); //Longest str length in each column

                final int offset = longest - displayWidth;
                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
                tableBuilder.append(align(cellAlign, sb, offset, styledCell, EMPTY));

                if (j < list.size() - 1) {
                    tableBuilder.append(vLine);
                }
                clearStringBuilder(sb);
            }

            if(i == 0){
                tableBuilder.append(Constants.NEWLINE).append(this.appendHeader(sb));
                clearStringBuilder(sb);
            }

            tableBuilder.append(Constants.NEWLINE);
        }

        return tableBuilder.toString();
    }

    //Dynamically calculate the header for the table
    private String appendHeader(StringBuilder sb){
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
        final StyleBuilder sb = this.tableConfiguration.getBorderStyle().styleBuilder();
        this.hLine = sb.formatReset(this.hLine, this.tableConfiguration.getBorderStyle().getHorizontalBorderStyles());
    }
}
