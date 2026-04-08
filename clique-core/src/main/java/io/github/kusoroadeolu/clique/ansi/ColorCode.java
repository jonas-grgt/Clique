package io.github.kusoroadeolu.clique.ansi;

import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

/**
 * Represents ANSI escape codes for basic text colors.
 * @since 1.0.0
 * */
@Stable(since = "3.2.0")
public enum ColorCode implements AnsiCode {
    // Enum constants with their corresponding ANSI codes
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BRIGHT_BLACK("\u001B[90m"),
    BRIGHT_RED("\u001B[91m"),
    BRIGHT_GREEN("\u001B[92m"),
    BRIGHT_YELLOW("\u001B[93m"),
    BRIGHT_BLUE("\u001B[94m"),
    BRIGHT_MAGENTA("\u001B[95m"),
    BRIGHT_CYAN("\u001B[96m"),
    BRIGHT_WHITE("\u001B[97m");

    // Field to hold the ANSI code string
    private final String code;

    /**
     * Constructs a ColorCode enum value.
     *
     * @param code The ANSI escape code string.
     */
    ColorCode(String code) {
        this.code = code;
    }

    /**
     * Overrides the toString method to return the ANSI code itself,
     * which makes using the enum simpler when printing color.
     *
     * @return The ANSI escape code string.
     */
    @Override
    public String ansiSequence() {
        return code;
    }

    @Override
    public String toString(){
        return code;
    }
}

