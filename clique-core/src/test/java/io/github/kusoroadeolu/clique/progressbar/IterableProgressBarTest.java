package io.github.kusoroadeolu.clique.progressbar;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IterableProgressBarTest {

    @Test
    void test_whenIteratedOver_shouldBeDone(){
        IterableProgressBar<Integer> iterable = Clique.progressBar(List.of(1,2,3,4,5));
        for (int i : iterable){}
        assertTrue(iterable.isDone());

    }


    @Test
    void test_whenIteratedOverOnce_throwsOnSubsequentIterations(){
        IterableProgressBar<Integer> iterable = Clique.progressBar(List.of(1,2,3,4,5));
        for (int i : iterable){}

        assertTrue(iterable.isDone());

        assertThrows(IllegalStateException.class, () -> {
            for (int i : iterable){}
        });
    }

    @Test
    void test_iteratorNext_shouldNotTick_beforeNextIsCalled(){
        IterableProgressBar<Integer> iterable = Clique.progressBar(List.of(1,2 , 3));
        var iterator = iterable.iterator();
        assertTrue(iterable.progressBar.currentTick < 1);
        iterator.next();
        assertEquals(1, iterable.progressBar.currentTick);
    }

}