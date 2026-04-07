package io.github.kusoroadeolu.clique.themeloader;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;

import java.util.Map;

@InternalApi(since = "3.1.3")
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
        return "Kush V";
    }

    @Override
    public String url() {
        return "";
    }

    private record TestAnsiCode(String code) implements AnsiCode {

        @Override
        public String toString() {
            return code;
        }
    }
}