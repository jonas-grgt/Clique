package io.github.kusoroadeolu.clique.core.utils;

public class Constants {
    public static final String NEWLINE = "\n";
    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final char BLANK_CHAR = ' ';
    public static final int ZERO = 0;
    public static final char ANSI_END = 'm';
    public static final char ANSI_BEGIN = '\u001b';

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
    public static final String CLI_COLOR_FORCE = "CLICOLOR_FORCE";
    public static final String COLOR_TERM = "COLORTERM";
    public static final String WT_SESSION = "WT_SESSION";
    public static final String FORCE_COLOR = "FORCE_COLOR";

    private Constants() {
        throw new AssertionError("cannot instantiate this");
    }
}
