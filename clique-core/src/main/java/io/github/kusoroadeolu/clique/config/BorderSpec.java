package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

@InternalApi(since = "3.1.3")
public interface BorderSpec {
    AnsiCode[] styles();
}
