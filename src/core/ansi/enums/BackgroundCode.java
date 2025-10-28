package core.ansi.enums;

import core.ansi.interfaces.AnsiCode;

public enum BackgroundCode implements AnsiCode {
        // Enum constants with their corresponding ANSI codes
        BLACK("\u001B[40m"),
        RED("\u001B[41m"),
        GREEN("\u001B[42m"),
        YELLOW("\u001B[43m"),
        BLUE("\u001B[44m"),
        MAGENTA("\u001B[45m"),
        CYAN("\u001B[46m"),
        WHITE("\u001B[47m");

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
         * Gets the ANSI escape code string for the background color.
         *
         * @return The ANSI escape code string.
         */
        public String getCode() {
            return code;
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


