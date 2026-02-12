package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.tables.structures.Cell;

import java.util.List;

public record BoxWrapper(
        int width,
        int length,
        BoxConfiguration boxConfiguration,
        List<Cell> wordWrap,
        String hLine,
        String vLine,
        String tLeft,
        String tRight,
        String bRight,
        String bLeft
) {
}
