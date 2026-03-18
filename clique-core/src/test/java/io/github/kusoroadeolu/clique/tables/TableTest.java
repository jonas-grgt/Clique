package io.github.kusoroadeolu.clique.tables;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.config.CellAlign;
import io.github.kusoroadeolu.clique.config.TableConfiguration;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
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

    @Test
    void assertPadding_shouldHaveThreeSpaces_whenLeftAligned(){
        var config = TableConfiguration.immutableBuilder().padding(3).build();
        Table table = Clique.table(TableType.DEFAULT, config)
                .headers("A", "B")
                .row("1", "2");
        var lines = table.get().lines().toList();
        var firstCol = lines.get(1);
        String[] arr = firstCol.split("\\|");
        String threeSpaces = " ".repeat(3);
        assertEquals("A" + threeSpaces, arr[1]);
        assertEquals("B" + threeSpaces, arr[2]);
    }

    @Test
    void assertPadding_shouldHaveThreeSpaces_whenRightAligned(){
        var config = TableConfiguration.immutableBuilder()
                .columnAlignment(Map.of(0, CellAlign.RIGHT, 1, CellAlign.RIGHT))
                .padding(3)
                .build();
        Table table = Clique.table(TableType.DEFAULT, config)
                .headers("A", "B")
                .row("1", "2");
        var lines = table.get().lines().toList();
        var firstCol = lines.get(1);

        String[] arr = firstCol.split("\\|");
        String threeSpaces = " ".repeat(3);

        assertEquals(threeSpaces + "A", arr[1]);
        assertEquals(threeSpaces + "B" , arr[2]);
    }

    @Test
    void assertSame_onSubsequentGetCalls_withoutModification(){
        Table table = Clique.table(TableType.DEFAULT)
                .headers("A", "B")
                .row("1", "2");

        assertNotNull(table.get());
        assertSame(table.get(), table.get());
    }

    @Test
    void assertNotSame_onSubsequentGetCalls_onRemoveCall(){ //This should cover for both update/remove since remove depends on update
        Table table = Clique.table(TableType.DEFAULT)
                .headers("A", "B")
                .row("1", "2");
        var str1 = table.get();
        table.removeCell(1, 1);

        assertNotSame(str1, table.get());
    }

    @Test
    void assertNotSame_onSubsequentGetCalls_onRowCall(){
        Table table = Clique.table(TableType.DEFAULT)
                .headers("A", "B")
                .row("1", "2");
        var str1 = table.get();
        table.row("1");

        assertNotSame(str1, table.get());
    }

    @Test
    void assertNotSame_onSubsequentGetCalls_onCustomizeCall(){
        CustomizableTable table = Clique.customizableTable(TableType.DEFAULT)
                .headers("A", "B")
                .customizeEdge('+');
        var str1 = table.get();
         table.customizeEdge('-');

        assertNotSame(str1, table.get());
    }

}