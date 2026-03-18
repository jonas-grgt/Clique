package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void borderStyleConfig_shouldApplyGivenChanges(){
        var autosize = BoxConfiguration.immutableBuilder().autoSize().build();
        var frame = Clique.box(BoxType.DEFAULT, autosize)
                .noDimensions()
                .content("Hello"); //ASCII //ASCII Box
        List<String> lines = frame.get().lines().toList();
        var line1 = lines.getFirst();
        var line2 = lines.get(1);
        assertTrue(line1.contains("+"));
        assertTrue(line1.contains("-")); //Asserting line 1 contains both the corners and horizontal chars
        assertTrue(line2.contains("|")); //Assert line2 contains the vlines

        BorderStyle style = BorderStyle
                .immutableBuilder()
                .cornerChar('o')
                .horizontalChar('~')
                .verticalChar('/')
                .build();
        var config = BoxConfiguration.immutableBuilder().autoSize().borderStyle(style).build();


        var frame2 = Clique.box(BoxType.DEFAULT, config)
                .noDimensions()
                .content("Hello"); //ASCII
        List<String> lines2 = frame2.get().lines().toList();
        var lines2_1 = lines2.getFirst();
        var lines2_2 = lines2.get(1);
        assertTrue(lines2_1.contains("o"));
        assertTrue(lines2_1.contains("~")); //Asserting line 1 contains both the corners and horizontal chars
        assertTrue(lines2_2.contains("/")); //Assert line2 contains the vlines
    }

    @Test
    void borderStyleConfig_shouldNotApplyChanges_onBlankChars(){
        BorderStyle style = BorderStyle
                .immutableBuilder()
                .cornerChar(' ')
                .build();

        var config = BoxConfiguration
                .immutableBuilder()
                .autoSize()
                .borderStyle(style)
                .build();

        var frame2 = Clique.box(BoxType.DEFAULT, config)
                .noDimensions()
                .content("Hello"); //ASCII
        List<String> lines2 = frame2.get().lines().toList();
        var lines2_1 = lines2.getFirst();
        assertFalse(lines2_1.contains(" "));
        assertTrue(lines2_1.contains("+"));
    }

}