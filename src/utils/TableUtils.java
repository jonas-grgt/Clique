package utils;

import tables.configuration.CellAlign;

public class TableUtils {

    private static final String BLANK = " ";

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
}
