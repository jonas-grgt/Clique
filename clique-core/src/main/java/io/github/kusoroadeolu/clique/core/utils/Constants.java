package io.github.kusoroadeolu.clique.core.utils;

import java.util.regex.Pattern;

public class Constants {
    public static final String NEWLINE = "\n";
    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final int ZERO = 0;
    public static final char ANSI_END = 'm';
    public static final char ANSI_BEGIN = '\u001b';
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\n");
    public static final Pattern SPACES_PATTERN = Pattern.compile("\\s+");
    public static final Pattern WHITESPACE_PATTERN = Pattern.compile("\u001b\\[[;\\d]*m");
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
    private Constants() {
        throw new AssertionError("cannot instantiate this");
    }
}
