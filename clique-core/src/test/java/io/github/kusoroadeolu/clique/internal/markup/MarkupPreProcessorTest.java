package io.github.kusoroadeolu.clique.internal.markup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static io.github.kusoroadeolu.clique.internal.markup.MarkupPostProcessor.postProcess;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkupPreProcessorTest {

    @ParameterizedTest
    @NullAndEmptySource
    void postProcess_nullOrEmpty_returnsAsIs(String input) {
        assertEquals(input, postProcess(input));
    }


    @Test
    void postProcess_plainText_unchanged() {
        String input = "nothing special here";
        assertEquals(input, postProcess(input));
    }


    @Test
    void escapedBracket_restoredCorrectly() {
        String input = "\\[bold]text[/]";
        String postProcessed = postProcess(input);
        assertEquals("[bold]text[/]", postProcessed);
    }



}