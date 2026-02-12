package io.github.kusoroadeolu.clique.ansi;

import io.github.kusoroadelu.clique.spi.AnsiCode;

/**
 * Represents ANSI escape codes for text styling (like italic, underline, etc.).
 */
public enum StyleCode implements AnsiCode {
    // Enum constants with their corresponding ANSI codes
    // The single constant representing the reset code
    RESET("\u001B[0m"),
    BOLD("\u001B[1m"),
    DIM("\u001B[2m"),
    ITALIC("\u001B[3m"),
    UNDERLINE("\u001B[4m"),
    REVERSE_VIDEO("\u001B[7m"),
    INVISIBLE_TEXT("\u001B[8m"),
    STRIKETHROUGH("\u001B[9m"),
    DOUBLE_UNDERLINE("\u001B[21m");

    // Field to hold the ANSI code string
    private final String code;

    /**
     * Constructs a FontStyleCode enum value.
     *
     * @param code The ANSI escape code string.
     */
    StyleCode(String code) {
        this.code = code;
    }


    /**
     * Returns the ANSI code itself, making it easy to use in print statements.
     *
     * @return The ANSI escape code string.
     */
    @Override
    public String toString() {
        return code;
    }
}

