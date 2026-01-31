package com.github.kusoroadeolu.clique.themes;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;

import java.util.Map;

public class TestTheme implements CliqueTheme {
    public String themeName() {
        return "test";
    }

    public Map<String, AnsiCode> styles() {
        return Map.of(
                "test-red", new TestAnsiCode("\u001B[31m"),
                "test-blue", new TestAnsiCode("\u001B[34m")
        );
    }

    private record TestAnsiCode(String code) implements AnsiCode {

        @Override
        public String toString() {
            return code;
        }
    }
}