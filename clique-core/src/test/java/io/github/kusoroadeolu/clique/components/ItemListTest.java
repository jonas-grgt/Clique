package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.configuration.ItemListConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemListTest {
    @Test
    void assertThrow_onSelfNest(){
        var list = new ItemList();
        assertThrows(IllegalArgumentException.class, () -> list.item("", "", list));
    }

    @Test
    void assert_onAdd_containsItem(){
        var list = new ItemList();
        String s = list.item("->", "Content").get();
        assertTrue(s.contains("Content"));
    }

    @Test
    void test_indentSpacing(){
        var list = new ItemList();
        String s = list.item("->", "Content", new ItemList().item("<-", "Content 2")).get();
        var lines = s.lines().toList();
        assertEquals(lines.get(1).substring(0, 2), " ".repeat(2)); //1 depth * default 2 indent level
    }

    @Test
    void test_symbolSpacing(){
        var list = new ItemList();
        String s = list.item("->", "Content").get();
        String sub = s.substring(2);
        assertTrue(sub.startsWith(" "));
    }


    @Test
    void assertConfigurationCoalesces(){
        var config = ItemListConfiguration.builder().indentSize(1).build();
        var list = new ItemList(config);
        list.item("->", "Content", new ItemList().item("<-", "Content 2"));
        var child = list.child();
        assertSame(child.configuration(), config);
    }
}