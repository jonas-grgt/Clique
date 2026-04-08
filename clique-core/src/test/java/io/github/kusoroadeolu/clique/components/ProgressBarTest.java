package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.configuration.ProgressBarConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressBarTest {

    @Test
    void testInitialState() {
        ProgressBar bar = new ProgressBar(100);
        assertEquals(0, bar.currentTick);
        assertEquals(100, bar.total);
        assertFalse(bar.isDone());
    }

    @Test
    void testTickIncrementsProgress() {
        ProgressBar bar = new ProgressBar(100);
        bar.tick();
        assertEquals(1, bar.currentTick);

        bar.tick(5);
        assertEquals(6, bar.currentTick);
    }

    @Test
    void testTickCannotExceedTotal() {
        ProgressBar bar = new ProgressBar(10);
        bar.tick(15);
        assertEquals(10, bar.currentTick);  // Should cap at total
    }

    @Test
    void testTickShouldThrowOnNegativeTick() {
        ProgressBar bar = new ProgressBar(100);
        assertThrows(IllegalArgumentException.class, () -> bar.tick(-50));
    }

    @Test
    void testComplete() {
        ProgressBar bar = new ProgressBar(100);
        bar.tick(50);
        bar.complete();

        assertEquals(100, bar.currentTick);
        assertTrue(bar.isDone);
    }


    @Test
    void testComplete_whenComplete_completeCallShouldNotThrow(){
        ProgressBar bar = new ProgressBar(100);
        bar.tick(100);
        assertDoesNotThrow(bar::complete);
    }

    @Test
    void testPercentCalculation() {
        ProgressBar bar = new ProgressBar(100);
        bar.tick(25);

        String output = bar.get();
        assertTrue(output.contains("25"));  // Should show 25%
    }

    @Test
    void testPercentWithZeroTotal() {
        ProgressBar bar = new ProgressBar(0);
        String output = bar.get();
        assertTrue(output.contains("  0"));  // Should handle gracefully
    }

    @Test
    void testFormatTokenReplacement() {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .format(":progress/:total (:percent%)")
                .build();

        ProgressBar bar = new ProgressBar(100, config);
        bar.tick(50);

        String output = bar.get();
        assertTrue(output.contains("50/100"));
        assertTrue(output.contains("50"));
    }

    @Test
    void testCustomCompleteAndIncomplete() {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .complete('=')
                .incomplete('-')
                .length(10)
                .format(":bar")
                .build();

        ProgressBar bar = new ProgressBar(10, config);
        bar.tick(5);

        String output = bar.get();
        assertTrue(output.contains("====="));
        assertTrue(output.contains("-----"));
    }

    @Test
    void testStyleRanges() {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .styleRange(0, 30, "LOW :percent%")
                .styleRange(30, 70, "MID :percent%")
                .styleRange(70, 100, "HIGH :percent%")
                .build();

        ProgressBar bar = new ProgressBar(100, config);

        bar.tick(20);
        assertTrue(bar.get().contains("LOW"));

        bar.tick(30);  // Now at 50%
        assertTrue(bar.get().contains("MID"));

        bar.tick(30);  // Now at 80%
        assertTrue(bar.get().contains("HIGH"));
    }

    @Test
    void testStylePredicates() {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .styleWhen(p -> p == 100, "COMPLETE!")
                .styleWhen(p -> p < 100, "LOADING :percent%")
                .build();

        ProgressBar bar = new ProgressBar(100, config);

        bar.tick(50);
        assertTrue(bar.get().contains("LOADING"));

        bar.complete();
        assertTrue(bar.get().contains("COMPLETE!"));
    }

    @Test
    void testStylePredicateOrderMatters() {
        // First matching predicate wins
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .styleWhen(p -> p >= 50, "FIRST")
                .styleWhen(p -> p >= 50, "SECOND")  // This won't match even at 50%
                .build();

        ProgressBar bar = new ProgressBar(100, config);
        bar.tick(75);

        assertTrue(bar.get().contains("FIRST"));
        assertFalse(bar.get().contains("SECOND"));
    }

    @Test
    void testFallbackToDefaultFormat() {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .format("DEFAULT :percent%")
                .styleWhen(p -> p > 200, "IMPOSSIBLE")  // Will never match
                .build();

        ProgressBar bar = new ProgressBar(100, config);
        bar.tick(50);

        assertTrue(bar.get().contains("DEFAULT"));
    }

    @Test
    void testElapsedTimeExists() {
        ProgressBar bar = new ProgressBar(100);
        bar.tick();
        String output = bar.get();
        assertFalse(output.contains("--:--"));
        assertTrue(output.contains("00:"));
    }

    @Test
    void testRemainingTimeWithNoProgress() {
        ProgressBar bar = new ProgressBar(100);
        String output = bar.get();
        assertTrue(output.contains("--:--"));  // No progress = can't estimate
    }

    @Test
    void testMarkupParsingInFormat() {
        ProgressBarConfiguration config = ProgressBarConfiguration.builder()
                .format("[red]:percent%[/]")
                .build();

        ProgressBar bar = new ProgressBar(100, config);
        bar.tick(50);

        String output = bar.get();
        assertFalse(output.contains("[red]")); //Should contain the actual ansi code instead
    }
}