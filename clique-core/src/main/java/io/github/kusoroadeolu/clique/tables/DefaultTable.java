package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.TableConfiguration;
import io.github.kusoroadeolu.clique.core.structures.WidthAwareList;
import io.github.kusoroadeolu.clique.core.utils.Constants;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Collection;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

class DefaultTable extends AbstractTable implements CustomizableTable {
    private String edge;
    private String hLine;
    private String vLine;

    public DefaultTable(TableConfiguration tableConfiguration) {
        super(tableConfiguration);
        this.edge = "+";
        this.hLine = "-";
        this.vLine = "|";
        this.styleTableBorders();

    }

    public String get() {
        if (cachedTable != null) return cachedTable;

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
                final int displayWidth = list.get(j).width();
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

        return (cachedTable = tableBuilder.toString());
    }

    //Dynamically calculate the header and footer for the table
    private String appendHeader(StringBuilder sb) {
        for (final WidthAwareList l : this.columns) {
            sb.append(edge);
            sb.repeat(hLine, l.longest() + this.tableConfiguration.getPadding());
        }
        sb.append(edge);
        return sb.toString();
    }

    public CustomizableTable customizeEdge(char edge) {
        this.edge = String.valueOf(edge);
        nullCachedTable();
        return this;
    }

    public CustomizableTable customizeVerticalLine(char vLine) {
        this.vLine = String.valueOf(vLine);
        nullCachedTable();
        return this;
    }

    public CustomizableTable customizeHorizontalLine(char hLine) {
        this.hLine = String.valueOf(hLine);
        nullCachedTable();
        return this;
    }


    protected void styleTableBorders() {
        if (this.tableConfiguration.getBorderStyle() == null) return;
        final BorderStyle borderStyle = this.tableConfiguration.getBorderStyle();
        final StyleBuilder sb = borderStyle.styleBuilder();
        this.hLine = sb.formatAndReset(this.hLine, borderStyle.getHorizontalBorderStyles());
        this.vLine = sb.formatAndReset(this.vLine, borderStyle.getVerticalBorderStyles());
        this.edge = sb.formatAndReset(this.edge, borderStyle.getEdgeBorderStyles());
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        DefaultTable that = (DefaultTable) object;
        return Objects.equals(edge, that.edge) && Objects.equals(hLine, that.hLine) && Objects.equals(vLine, that.vLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), edge, hLine, vLine);
    }

    @Override
    public String toString() {
        return "DefaultTable[" +
                "tableConfiguration=" + tableConfiguration +
                ", vLine='" + vLine + '\'' +
                ", hLine='" + hLine + '\'' +
                ", edge='" + edge + '\'' +
                ", rows=" + rows +
                ", columns=" + columns +
                ']';
    }
}
