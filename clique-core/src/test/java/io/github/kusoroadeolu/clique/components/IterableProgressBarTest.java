package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IterableProgressBarTest {
    private PrintStream none = new NonePrintStream();

    @Test
    void test_whenIteratedOver_shouldBeDone(){
        IterableProgressBar<Integer> iterable = Clique.progressBar(List.of(1,2,3,4,5));
        iterable.printStream(none);
        for (int i : iterable){
            //Do Nothing
        }
        assertTrue(iterable.isDone());

    }


    @Test
    void test_whenIteratedOverOnce_throwsOnSubsequentIterations(){
        IterableProgressBar<Integer> iterable = Clique.progressBar(List.of(1,2,3,4,5));
        iterable.printStream(none);
        for (int i : iterable){
            //Do Nothing

        }
        assertThrows(IllegalStateException.class, () -> {
            for (int i : iterable){
                //Do Nothing
            }
        });
    }

    @Test
    void test_iteratorNext_shouldNotTick_beforeNextIsCalled(){
        IterableProgressBar<Integer> iterable = Clique.progressBar(List.of(1,2 , 3));
        iterable.printStream(none);
        var iterator = iterable.iterator();
        iterator.next();
        assertEquals(1, iterable.progressBar.currentTick);
    }


    private static class NonePrintStream extends PrintStream {
        public NonePrintStream(){
            this(OutputStream.nullOutputStream());
        }

        public NonePrintStream(OutputStream out) {
            super(out);
        }
    }


}