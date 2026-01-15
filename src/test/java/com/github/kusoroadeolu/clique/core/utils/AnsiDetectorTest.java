package com.github.kusoroadeolu.clique.core.utils;

import com.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnsiDetectorTest {
    @Test
    void testAnsiDisabled() {
        Clique.enableCliqueColors(false);
        String output = Clique.parser().parse("[red]Text[/]");
        assertEquals("Text", output);
    }
}