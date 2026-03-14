package io.github.kusoroadeolu.clique.ansi;

import io.github.kusoroadeolu.clique.spi.AnsiCode;

public enum BackgroundCode implements AnsiCode {
    // Enum constants with their corresponding ANSI codes
    BLACK("\u001B[40m"),
    RED("\u001B[41m"),
    GREEN("\u001B[42m"),
    YELLOW("\u001B[43m"),
    BLUE("\u001B[44m"),
    MAGENTA("\u001B[45m"),
    CYAN("\u001B[46m"),
    WHITE("\u001B[47m"),
    BRIGHT_BLACK("\u001B[100m"),
    BRIGHT_RED("\u001B[101m"),
    BRIGHT_GREEN("\u001B[102m"),
    BRIGHT_YELLOW("\u001B[103m"),
    BRIGHT_BLUE("\u001B[104m"),
    BRIGHT_MAGENTA("\u001B[105m"),
    BRIGHT_CYAN("\u001B[106m"),
    BRIGHT_WHITE("\u001B[107m");

    // Field to hold the ANSI code string
    private final String code;

    /**
     * Constructs a BackgroundCode enum value.
     *
     * @param code The ANSI escape code string.
     */
    BackgroundCode(String code) {
        this.code = code;
    }


    /**
     * Overrides the toString method to return the ANSI code itself,
     * making it simpler to concatenate with strings.
     *
     * @return The ANSI escape code string.
     */
    @Override
    public String toString() {
        return code;
    }
}


