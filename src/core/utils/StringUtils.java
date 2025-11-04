package core.utils;

import parser.token.stringparser.interfaces.AnsiStringParser;
import core.misc.Cell;

public final class StringUtils {

    public final static int ZERO = 0;

    public static void clearStringBuilder(StringBuilder sb){
        sb.setLength(ZERO);
    }

    public static Cell parseCell(String cell, AnsiStringParser parser){
        if(parser != null){
            final String styled = parser.parse(cell);
            return new Cell(parser.getOriginalString(), styled);
        }else{
            return new Cell(cell, cell);
        }
    }



}
