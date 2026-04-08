package io.github.kusoroadeolu.clique.internal;

import io.github.kusoroadeolu.clique.configuration.BoxConfiguration;
import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;

import java.util.List;

@InternalApi(since = "3.2.1")
public record BoxWrapper(
        int width,
        int height,
        BoxConfiguration configuration,
        List<Cell> wordWrap,
        String hLine,
        String vLine,
        String tLeft,
        String tRight,
        String bRight,
        String bLeft
) {
}
