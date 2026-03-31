package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.structures.Cell;

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
