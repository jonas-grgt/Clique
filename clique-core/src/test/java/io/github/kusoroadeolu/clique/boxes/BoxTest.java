package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxTest {
    @Test
    void testBoxWidth() {
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 10)
                .content("Test");
        String output = box.get();
        String[] lines = output.split("\n");
        assertTrue(lines[0].length() <= 52); // Width + borders
    }
}