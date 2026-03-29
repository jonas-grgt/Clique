package io.github.kusoroadeolu.clique.tables;


import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.TableConfiguration;
import io.github.kusoroadeolu.clique.core.structures.WidthAwareList;
import io.github.kusoroadeolu.clique.core.utils.Constants;
import io.github.kusoroadeolu.clique.style.DefaultStyleBuilder;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.align;
import static io.github.kusoroadeolu.clique.core.utils.TableUtils.chooseColAlignment;

class DefaultTable extends AbstractTable implements CustomizableTable {
    private String corner;
    private String hLine;
    private String vLine;

    public DefaultTable(TableConfiguration tableConfiguration) {
        super(tableConfiguration);
        this.corner = "+";
        this.hLine = "-";
        this.vLine = "|";
        this.styleTableBorders();

    }

    public String get() {
        if (cachedString != null) return cachedString;

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
                final int longest = cl.longest(); //Longest str height in each column

                final int offset = (longest - displayWidth) + padding;

                cellAlign = chooseColAlignment(j, cellAlign, this.tableConfiguration.getColumnAlignment());
                tableBuilder.append(align(cellAlign, sb, offset, styledCell, vLine));
                clearStringBuilder(sb);
            }

            tableBuilder.append(Constants.NEWLINE);
            tableBuilder.append(headerAndFooter).append(Constants.NEWLINE);
        }

        return (cachedString = tableBuilder.toString());
    }

    //Dynamically calculate the header and footer for the table
    private String appendHeader(StringBuilder sb) {
        for (final WidthAwareList l : this.columns) {
            sb.append(corner);
            sb.repeat(hLine, l.longest() + this.tableConfiguration.getPadding());
        }
        sb.append(corner);
        return sb.toString();
    }

    public void customizeCorner(char corner) {
        var str = Character.toString(corner);
        if (!str.isBlank()){
            this.corner = str;
            nullCachedString();
        }

    }

    public void customizeVLine(char vLine) {
        var str = Character.toString(vLine);
        if (!str.isBlank()){
            this.vLine = str;
            nullCachedString();
        }
    }

    public void customizeHLine(char hLine) {
        var str = Character.toString(hLine);
        if (!str.isBlank()){
            this.hLine = str;
            nullCachedString();
        }
    }


    protected void styleTableBorders() {
        final BorderStyle borderStyle = this.tableConfiguration.getBorderStyle();
        if (borderStyle != null){
            final StyleBuilder sb = new DefaultStyleBuilder();
            this.hLine = sb.formatAndReset(this.hLine, borderStyle.getHorizontalStyle());
            this.vLine = sb.formatAndReset(this.vLine, borderStyle.getVerticalStyle());
            this.corner = sb.formatAndReset(this.corner, borderStyle.getCornerStyle());
            this.customizeCorner(borderStyle.getCornerChar());
            this.customizeHLine(borderStyle.getHorizontalChar());
            this.customizeVLine(borderStyle.getVerticalChar());
        }

    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        DefaultTable that = (DefaultTable) object;
        return Objects.equals(corner, that.corner) && Objects.equals(hLine, that.hLine) && Objects.equals(vLine, that.vLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), corner, hLine, vLine);
    }

    @Override
    public String toString() {
        return "DefaultTable[" +
                "tableConfiguration=" + tableConfiguration +
                ", vLine='" + vLine + '\'' +
                ", hLine='" + hLine + '\'' +
                ", edge='" + corner + '\'' +
                ", rows=" + rows +
                ", columns=" + columns +
                ']';
    }

    @Override
    public CustomizableTable customizeEdge(char edge) {
        this.customizeCorner(edge);
        return this;
    }

    @Override
    public CustomizableTable customizeVerticalLine(char vLine) {
        this.customizeVLine(vLine);
        return this;
    }

    @Override
    public CustomizableTable customizeHorizontalLine(char hLine) {
        this.customizeHLine(hLine);
        return this;
    }
}
