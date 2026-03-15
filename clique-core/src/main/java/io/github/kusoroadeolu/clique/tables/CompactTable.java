package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.config.TableConfiguration;
import io.github.kusoroadeolu.clique.core.utils.Constants;
import io.github.kusoroadeolu.clique.style.StyleBuilder;
import io.github.kusoroadeolu.clique.core.structures.WidthAwareList;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

public class CompactTable extends AbstractTable {
    private final String vLine;
    private String hLine;

    public CompactTable(TableConfiguration tableConfiguration) {
        super(tableConfiguration);
        this.vLine = Constants.BLANK.repeat(this.tableConfiguration.getPadding());
        this.hLine = "-";
        this.styleTableBorders();
    }

    public String get() {
        final var tableBuilder = new StringBuilder();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.rows.size(); i++) {
            final WidthAwareList list = this.rows.get(i);

            for (int j = 0; j < list.size(); j++) {
                var cellAlign = this.tableConfiguration.getAlignment();
                final String styledCell = list.getStyledText(j);
                final int displayWidth = list.get(j).width();
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

            if (i == 0) {
                tableBuilder.append(Constants.NEWLINE).append(this.appendHeader(sb));
                clearStringBuilder(sb);
            }

            tableBuilder.append(Constants.NEWLINE);
        }

        return tableBuilder.toString();
    }

    //Dynamically calculate the header for the table
    private String appendHeader(StringBuilder sb) {
        for (int i = 0; i < this.columns.size(); i++) {
            final WidthAwareList col = this.columns.get(i);
            sb.repeat(hLine, col.longest());

            if (i < this.columns.size() - 1) {
                sb.append(vLine);
            }
        }

        return sb.toString();
    }


    protected void styleTableBorders() {
        if (this.tableConfiguration.getBorderStyle() == null) return;
        final StyleBuilder sb = this.tableConfiguration.getBorderStyle().styleBuilder();
        this.hLine = sb.formatAndReset(this.hLine, this.tableConfiguration.getBorderStyle().getHorizontalBorderStyles());
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        CompactTable that = (CompactTable) object;
        return Objects.equals(hLine, that.hLine) && Objects.equals(vLine, that.vLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hLine, vLine);
    }

    @Override
    public String toString() {
        return "CompactTable[" +
                "hLine='" + hLine + '\'' +
                ", vLine='" + vLine + '\'' +
                ", columns=" + columns +
                ", rows=" + rows +
                ", tableConfiguration=" + tableConfiguration +
                ']';
    }
}
