package io.github.kusoroadeolu.clique.themes;

import io.github.kusoroadeolu.clique.spi.AnsiCode;

record CustomAnsiCode(String code) implements AnsiCode {
    @Override
    public String ansiSequence() {
        return code;
    }
}