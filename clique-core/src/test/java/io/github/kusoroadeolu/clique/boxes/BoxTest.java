package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    @Test
    void testBoxWidth() {
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 10)
                .content("Test");
        String output = box.get();
        var lines = output.lines().toList();
        assertEquals(50, lines.getFirst().length()); // Width + borders
    }

    @Test
    void assertThrows_whenWidthIsZero(){
        assertThrows(IllegalArgumentException.class,
                () -> Clique.box()
                .withDimensions(0, 10));
    }

    @Test
    void assertThrows_whenWidthIsNegative(){
        assertThrows(IllegalArgumentException.class,
                () -> Clique.box()
                        .withDimensions(-1, 10));
    }

    @Test
    void assertThrows_whenHeightIsNegative(){
        assertThrows(IllegalArgumentException.class,
                () -> Clique.box()
                        .withDimensions(10, -1));
    }

    @Test
    void assertThrows_whenHeightIsZero(){
        assertThrows(IllegalArgumentException.class,
                () -> Clique.box()
                        .withDimensions(10, 0));
    }

    @Test
    void assertThrows_onNoDimensions_ifAutoSizeConfigIsAbsent(){
        assertThrows(IllegalStateException.class,
                () -> Clique.box()
                        .noDimensions());
    }

    @Test
    void assertDoesNotThrow_onNoDimensions_ifAutoSizeConfigIsAbsent(){
        assertDoesNotThrow(() -> Clique.box(BoxConfiguration.immutableBuilder().autoSize().build())
                        .noDimensions());
    }

    @Test
    void assertSame_onSubsequentGets_withoutModification(){
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 10)
                .content("Test");

        String output = box.get();

        assertSame(output, box.get());
    }


}