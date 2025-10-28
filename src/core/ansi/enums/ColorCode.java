package core.ansi.enums;

import core.ansi.interfaces.AnsiCode;

/**
 * Represents ANSI escape codes for basic text colors.
 */
public enum ColorCode implements AnsiCode {
    // Enum constants with their corresponding ANSI codes
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

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
     * Gets the ANSI escape code string for the color.
     *
     * @return The ANSI escape code string.
     */
    public String getCode() {
        return code;
    }

    /**
     * Overrides the toString method to return the ANSI code itself,
     * which makes using the enum simpler when printing color.
     *
     * @return The ANSI escape code string.
     */
    @Override
    public String toString() {
        return code;
    }
}

