package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.core.display.Generated;

import java.io.PrintStream;
import java.util.Collection;

public interface Table extends Generated {
    Table addRows(String... rows);
    Table addRows(Collection<String> rows);

    Table removeRow(int index);
    Table removeCell(int row, int col);
    Table updateCell(int row, int col, String text);
}
