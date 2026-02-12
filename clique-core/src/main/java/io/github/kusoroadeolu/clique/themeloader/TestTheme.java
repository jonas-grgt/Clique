package io.github.kusoroadeolu.clique.themeloader;


import io.github.kusoroadelu.clique.spi.AnsiCode;
import io.github.kusoroadelu.clique.spi.CliqueTheme;

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

    @Override
    public String author() {
        return "Kusoro Adeolu";
    }

    @Override
    public String url() {
        return "someurl";
    }

    private record TestAnsiCode(String code) implements AnsiCode {

        @Override
        public String toString() {
            return code;
        }
    }
}