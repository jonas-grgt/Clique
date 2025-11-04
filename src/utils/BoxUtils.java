package utils;

import boxes.enums.TextAlign;

import java.util.List;

public class BoxUtils {

    public final static String BLANK = TableUtils.BLANK;

    public static void alignText(StringBuilder sb, int idx, TextAlign textAlign, String spaces, List<String> wordWrap, String vLine){
        final String s = wordWrap.get(idx - 1);
        final int totalPadding = spaces.length() - s.length();

        switch (textAlign){
            case TOP_LEFT, CENTER_LEFT, BOTTOM_LEFT -> {
                final String padding = BLANK.repeat(Math.max(0, totalPadding));
                sb.append(vLine)
                        .append(s)
                        .append(padding)
                        .append(vLine)
                        .append("\n");
            }

            case TOP_RIGHT, CENTER_RIGHT, BOTTOM_RIGHT -> {

                final String padding = BLANK.repeat(Math.max(0, totalPadding));
                sb.append(vLine)
                        .append(padding)
                        .append(s)
                        .append(vLine)
                        .append("\n");
            }

            case TOP_CENTER, CENTER, BOTTOM_CENTER -> {

                if(s.length() >= spaces.length()){
                    sb.append(vLine).append(s).append(vLine).append("\n");
                    return;
                }

                final int leftPadding = totalPadding / 2;
                final int rightPadding = totalPadding - leftPadding;

                sb.append(vLine)
                        .append(BLANK.repeat(leftPadding))
                        .append(s)
                        .append(BLANK.repeat(rightPadding))
                        .append(vLine)
                        .append("\n");
            }
        }
    }
}
