package com.github.kusoroadeolu.clique.tables;

import com.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    void testTableStructure() {
        Table table = Clique.table(TableType.DEFAULT)
                .addHeaders("A", "B")
                .addRows("1", "2");
        String output = table.buildTable();

        assertNotNull(output);
        assertTrue(output.contains("A"));
        assertTrue(output.contains("B"));
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));
    }

    @Test
    void testTableCellUpdate() {
        Table table = Clique.table(TableType.DEFAULT)
                .addHeaders("A", "B")
                .addRows("1", "2");
        table.updateCell(1, 0, "99");
        String output = table.buildTable();
        assertTrue(output.contains("99"));
    }

}