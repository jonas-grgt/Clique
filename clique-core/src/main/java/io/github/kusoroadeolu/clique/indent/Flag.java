package io.github.kusoroadeolu.clique.indent;

public enum Flag {
    HYPHEN("-"),
    BULLET("•"),
    SQUARE_BULLET("▪"),
    ASTERISK("*"),
    ARROW("→"),
    CIRCLE("○"),
    RIGHT_TRIANGLE("▸");

    private final String flag;

    Flag(String flag) {
        this.flag = flag;
    }

    public String flag() {
        return this.flag;
    }

    @Override
    public String toString() {
        return this.flag;
    }
}
