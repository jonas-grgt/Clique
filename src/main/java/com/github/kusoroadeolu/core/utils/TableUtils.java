package com.github.kusoroadeolu.core.utils;

import com.github.kusoroadeolu.core.misc.CellAlign;
import com.github.kusoroadeolu.tables.structures.WidthAwareList;

import java.util.List;
import java.util.Map;

public class TableUtils {

    public static final String BLANK = " ";

    public static String align(CellAlign cellAlign, StringBuilder sb, int offset, String cell ,String vLine){
        final String spaces = BLANK.repeat(offset);
        return switch (cellAlign){
            case LEFT -> sb.append(cell).append(spaces).append(vLine).toString();
            case RIGHT -> sb.append(spaces).append(cell).append(vLine).toString();
            case CENTER -> {
                final int len = spaces.length(); //Get the length of the spaces
                final int rem = len % 2;
                final int leftOffset = (len - rem) - (len / 2);
                final int rightOffset = len - leftOffset;
                yield sb.append(BLANK.repeat(leftOffset)).append(cell).append(BLANK.repeat(rightOffset)).append(vLine).toString();
            }
        };
    }

    public static String handleNulls(String val, String nullReplacement){
        if(val != null) return val;
        return nullReplacement;
    }

    public static void validateRowIndex(int rowIdx, List<WidthAwareList> rows){
        if(rowIdx > (rows.size() - 1)) throw new IllegalArgumentException("Row: " + rowIdx + "does not exist");
    }

    public static void validateHeaders(int idx, int col){
        if(idx == 0 || col == 0) throw new IllegalArgumentException("Cannot remove a header from a table");
    }

    public static void validateColumnIndex(int colIdx, List<WidthAwareList> cols){
        if(colIdx > (cols.size() - 1)) throw new IllegalArgumentException("Column: " + colIdx + "does not exist");
    }

    public static CellAlign chooseColAlignment(int colIdx, CellAlign defAlign, Map<Integer, CellAlign> cAlign){
        return cAlign.get(colIdx) == null ? defAlign : cAlign.get(colIdx);
    }


}
