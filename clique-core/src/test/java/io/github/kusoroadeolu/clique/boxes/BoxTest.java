package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static io.github.kusoroadeolu.clique.core.utils.Constants.NEWLINE;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxTest {
    @Test
    void testBoxWidth() {
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 10)
                .content("Test");
        String output = box.get();
        var lines = output.lines().toList();
        System.out.println(lines.getFirst().length());
        assertTrue(lines.getFirst().length() == 50); // Width + borders
    }
}