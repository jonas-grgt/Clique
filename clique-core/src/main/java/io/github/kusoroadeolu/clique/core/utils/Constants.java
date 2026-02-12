package io.github.kusoroadeolu.clique.core.utils;

import java.util.regex.Pattern;

public class Constants {
    private Constants(){
        throw new AssertionError("cannot instantiate this");
    }

    public final static String NEWLINE = "\n";
    public final static String EMPTY = "";
    public final static String BLANK = " ";
    public final static int ZERO = 0;
    public final static char ANSI_END = 'm';
    public final static char ANSI_BEGIN = '\u001b';
    public final static Pattern NEWLINE_PATTERN = Pattern.compile("\\n");
    public final static Pattern SPACES_PATTERN = Pattern.compile("\\s+");
    public final static Pattern WHITESPACE_PATTERN = Pattern.compile("\u001b\\[[;\\d]*m");


    //FOR ANSI DETECTOR
    public static final String TERM = "TERM";
    public static final String PLAIN = "plain";
    public static final String DUMB = "dumb";
    public static final String NO_COLOR = "NO_COLOR";
    public static final String CLIQUE_COLOR = "clique.color";
    public static final String ALWAYS = "always";
    public static final String NEVER = "never";
    public static final String OS_NAME = "os.name";
    public static final String WIN = "win";
}
