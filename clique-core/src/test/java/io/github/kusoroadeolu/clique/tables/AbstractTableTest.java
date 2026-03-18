package io.github.kusoroadeolu.clique.tables;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.config.TableConfiguration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTableTest {
    @Test
    void testTableStructure() {
        Table table = Clique.table()
                .headers("A", "B")
                .row("1", "2");
        String output = table.get();
        List<String> lines = output.lines().toList();

        assertNotNull(output);
        assertTrue(lines.get(1).contains("A"));
        assertTrue(lines.get(1).contains("B"));
        assertTrue(lines.get(3).contains("1"));
        assertTrue(lines.get(3).contains("2"));
    }

    @Test
    void testTableCellUpdate() {
        Table table = Clique.table()
                .headers("A", "B")
                .row("1", "2");
        table.updateCell(1, 0, "99");
        String output = table.get();
        assertTrue(output.contains("99"));
    }

    @Test
    void testTableCellRemove() {
        var config = TableConfiguration.immutableBuilder().nullReplacement("N/A").build();
        Table table = Clique.table(config)
                .headers("A", "B")
                .row("1", "2");
        table.removeCell(1, 0);

        String output = table.get();
        List<String> lines = output.lines().toList();
        assertNotNull(output);
        assertFalse(lines.get(3).contains("1"));
        assertTrue(lines.get(3).contains("N/A"));
    }

    @Test
    void assertThrows_onHeaderRemoval() {
        var config = TableConfiguration.immutableBuilder().nullReplacement("N/A").build();
        Table table = Clique.table(config)
                .headers("A", "B")
                .row("1", "2");
        assertThrows(IllegalArgumentException.class, () -> table.removeCell(0, 1));
    }

    @Test
    void assertThrows_onInvalidRowIndex(){
        Table table = Clique.table()
                .headers("A", "B")
                .row("1", "2");
        assertThrows(IllegalArgumentException.class, () -> table.removeCell(3, 1));
    }

    @Test
    void assertThrows_onInvalidColIndex(){
        Table table = Clique.table()
                .headers("A", "B")
                .row("1", "2");
        assertThrows(IllegalArgumentException.class, () -> table.removeCell(1, 3));
    }


}