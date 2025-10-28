package core.ansi.enums;

import core.ansi.interfaces.AnsiCode;

/**
 * Represents the ANSI escape code to reset all text attributes.
 */
public enum ResetCode implements AnsiCode {
    // The single constant representing the reset code
    RESET("\u001B[0m");

    // Field to hold the ANSI code string
    private final String code;

    /**
     * Constructs the AnsiReset enum value.
     *
     * @param code The ANSI escape code string.
     */
    ResetCode(String code) {
        this.code = code;
    }

    /**
     * Gets the ANSI escape code string.
     *
     * @return The ANSI escape code string.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the ANSI code itself.
     *
     * @return The ANSI escape code string.
     */
    @Override
    public String toString() {
        return code;
    }
}
