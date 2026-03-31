package io.github.kusoroadeolu.clique.indent;

import io.github.kusoroadeolu.clique.core.documentation.Stable;

@Stable(since = "3.2.0")
public enum Flag {
    HYPHEN("-"),
    BULLET("•"),
    SQUARE_BULLET("▪"),
    ASTERISK("*"),
    ARROW("→"),
    CIRCLE("○"),
    RIGHT_TRIANGLE("▸");

    private final String flagString;

    Flag(String flagString) {
        this.flagString = flagString;
    }

    public String flag() {
        return this.flagString;
    }

    @Override
    public String toString() {
        return this.flagString;
    }
}
