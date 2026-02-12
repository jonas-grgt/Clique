package io.github.kusoroadelu.clique.spi;

public interface AnsiCode {
    /**
     * Returns the ANSI escape code string.
     * This method is automatically called during string concatenation.
     *
     * @return the ANSI escape sequence
     */
    String toString();
}