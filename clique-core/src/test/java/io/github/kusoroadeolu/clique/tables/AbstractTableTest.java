package io.github.kusoroadeolu.clique.tables;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractTableTest {
    @Test
    void testTableStructure() {
        Table table = Clique.table(TableType.DEFAULT)
                .addHeaders("A", "B")
                .addRows("1", "2");
        String output = table.get();

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
        String output = table.get();
        assertTrue(output.contains("99"));
    }

}