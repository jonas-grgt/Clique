package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.config.TextAlign;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    @Test
    void testBoxWidth() {
        var config = BoxConfiguration.immutableBuilder().textAlign(TextAlign.TOP_CENTER).build();
        var box = Clique.box(BoxType.ROUNDED, config)
                .withDimensions(50, 9)
                .content("Test");
        var output = box.get();
        var lines = output.lines().toList();
        assertEquals(52, lines.getFirst().length()); // Width + borders 50 + 2 borders
    }

    @Test
    void testBoxHeight(){
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 9)
                .content("Test");
        var content = box.get();
        assertEquals(11, content.lines().toList().size()); //9 plus 2 borders
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
                .withDimensions(50, 9)
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


    //TESTS FOR API CHANGES
    @Test
    void alignTopLeft_shouldBeShifted_toLeft(){
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 9)
                .content("Test", TextAlign.TOP_LEFT);
        var lines = AnsiStringParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1); //So it should be border + padding(2), so 3 substring before we get our content
        String firstChar = second.substring(3, 4);
        assertEquals("T", firstChar);
    }


    @Test
    void alignTopRight_shouldBeShifted_toRight(){
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 9)
                .content("Test", TextAlign.TOP_RIGHT);
        var lines = AnsiStringParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);
        //NORMALLY these will be cramped at their respective positions

    }

    @Test
    void textAlign_whenNull_shouldUseBoxConfigAlign(){
        var box = Clique.box(BoxType.ROUNDED, BoxConfiguration.immutableBuilder().textAlign(TextAlign.TOP_RIGHT).build())
                .withDimensions(50, 9)
                .content("Test");
        var lines = AnsiStringParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);
    }

    @Test
    void textAlign_whenSet_shouldUseGivenAlign(){
        var box = Clique.box(BoxType.ROUNDED, BoxConfiguration.immutableBuilder().textAlign(TextAlign.TOP_LEFT).build())
                .withDimensions(50, 9)
                .content("Test", TextAlign.TOP_RIGHT);
        var lines = AnsiStringParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);  //Should be top right
    }


    @Test
    void textAlign_shouldBeReassigned_onSubsequentAlignCalls(){
        var box = Clique.box(BoxType.ROUNDED)
                .withDimensions(50, 9)
                .content("Test", TextAlign.TOP_RIGHT);
        var lines = AnsiStringParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);  //Should be top right

        box.content("Test", TextAlign.TOP_LEFT);

        // Assert Top left
        var lines1 = AnsiStringParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second1  = lines1.get(1); //So it should be border + padding(2), so 3 substring before we get our content
        String firstChar1 = second1.substring(3, 4);
        assertEquals("T", firstChar1);

    }

}