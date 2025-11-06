package com.github.kusoroadeolu.boxes.concrete;

import com.github.kusoroadeolu.boxes.configuration.BoxConfiguration;
import com.github.kusoroadeolu.core.misc.Cell;

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
