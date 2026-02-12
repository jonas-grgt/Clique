package io.github.kusoroadelu.clique.spi;

import java.util.Map;

public interface CliqueTheme {
    String themeName();
    Map<String, AnsiCode> styles();
    String author();
    String url();
}
