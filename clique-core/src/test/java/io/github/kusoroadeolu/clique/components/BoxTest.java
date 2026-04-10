package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.configuration.BoxConfiguration;
import io.github.kusoroadeolu.clique.configuration.BoxType;
import io.github.kusoroadeolu.clique.configuration.TextAlign;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    @Test
    void testBoxWidth() {
        var box = Clique.box()
                .dimensions(50, 9)
                .content("Test", TextAlign.TOP_CENTER);
        var output = MarkupParser.DEFAULT.getOriginalString(box.get());
        var lines = output.lines().toList();
        assertEquals(52, lines.getFirst().length()); // Width + borders 50 + 2 borders
    }

    @Test
    void testBoxHeight(){
        var box = Clique.box(BoxType.ROUNDED)
                .dimensions(50, 9)
                .content("Test");
        var content = box.get();
        assertEquals(11, content.lines().toList().size()); //9 plus 2 borders
    }

    @Test
    void assertThrows_whenWidthIsZero(){
        Box box = Clique.box();
        assertThrows(IllegalArgumentException.class,
                () -> box.dimensions(0, 10));
    }

    @Test
    void assertThrows_whenWidthIsNegative(){
        Box box = Clique.box();
        assertThrows(IllegalArgumentException.class,
                () -> box.dimensions(-1, 10));
    }

    @Test
    void assertThrows_whenHeightIsNegative(){
        Box box = Clique.box();
        assertThrows(IllegalArgumentException.class,
                () -> box.dimensions(10, -1));
    }

    @Test
    void assertThrows_whenHeightIsZero(){
        Box box = Clique.box();
        assertThrows(IllegalArgumentException.class,
                () -> box.dimensions(10, 0));
    }


    //Simple regression test
    @Test
    void assertDoesNotThrow_whenNoWidthSet(){
        assertDoesNotThrow(() -> Clique.box());
    }

    @Test
    void assertSame_onSubsequentGets_withoutModification(){
        var box = Clique.box(BoxType.ROUNDED)
                .dimensions(50, 9)
                .content("Test");

        String output = box.get();
        assertNotNull(output);
        var output1 = box.get();
        assertSame(output1, output);
    }

    //TESTS FOR API CHANGES
    @Test
    void alignTopLeft_shouldBeShifted_toLeft(){
        var box = Clique.box(BoxType.ROUNDED)
                .dimensions(50, 9)
                .content("Test", TextAlign.TOP_LEFT);
        var lines = MarkupParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1); //So it should be border + padding(2), so 3 substring before we get our content
        String firstChar = second.substring(3, 4);
        assertEquals("T", firstChar);
    }


    @Test
    void alignTopRight_shouldBeShifted_toRight(){
        var box = Clique.box(BoxType.ROUNDED)
                .dimensions(50, 9)
                .content("Test", TextAlign.TOP_RIGHT);
        var lines = MarkupParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);
        //NORMALLY these will be cramped at their respective positions

    }

    @Test
    void textAlign_whenNull_shouldUseBoxConfigAlign(){
        var box = Clique.box(BoxType.ROUNDED, BoxConfiguration.builder().textAlign(TextAlign.TOP_RIGHT).build())
                .dimensions(50, 9)
                .content("Test");
        var lines = MarkupParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);
    }

    @Test
    void textAlign_whenSet_shouldUseGivenAlign(){
        var box = Clique.box(BoxType.ROUNDED, BoxConfiguration.builder().textAlign(TextAlign.TOP_LEFT).build())
                .dimensions(50, 9)
                .content("Test", TextAlign.TOP_RIGHT);
        var lines = MarkupParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second  = lines.get(1);
        String firstChar = second.substring(second.length() - 7, second.length() - 6);
        assertEquals("T", firstChar);  //Should be top right
    }


    @Test
    void textAlign_shouldBeReassigned_onSubsequentAlignCalls(){
        var box = Clique.box()
                .dimensions(50, 9)
                .content("Test", TextAlign.TOP_LEFT);

        // Assert Top left
        var lines1 = MarkupParser.DEFAULT.getOriginalString(box.get()).lines().toList(); //Strip resets
        String second1  = lines1.get(1); //So it should be border + padding(2), so 3 substring before we get our content
        String firstChar1 = second1.substring(3, 4);
        assertEquals("T", firstChar1);

    }

}