package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    @Test
    void testBoxWidth() {
        Box box = Clique.box(BoxType.ROUNDED)
                .width(50)
                .length(10)
                .content("Test");
        String output = box.buildBox();
        // Verify box respects width constraint
        String[] lines = output.split("\n");
        assertTrue(lines[0].length() <= 52); // Width + borders
    }
}