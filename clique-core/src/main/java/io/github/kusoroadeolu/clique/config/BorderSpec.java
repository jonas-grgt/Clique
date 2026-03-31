package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

@InternalApi(since = "3.2.0")
public interface BorderSpec {
    AnsiCode[] styles();
}
