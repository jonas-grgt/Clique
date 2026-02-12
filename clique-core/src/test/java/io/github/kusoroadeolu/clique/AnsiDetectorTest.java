package io.github.kusoroadeolu.clique;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnsiDetectorTest {
    @Test
    void testAnsiDisabled() {
        Clique.enableCliqueColors(false);
        String output = Clique.parser().parse("[red]Text[/]");
        assertEquals("Text", output);
    }
}