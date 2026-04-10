package io.github.kusoroadeolu.clique.style;

import io.github.kusoroadeolu.clique.configuration.StyleContext;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StyleContextTest {

    @Test
    void onAdd_assertContainsAnsiCode(){
       var ctx = StyleContext.builder().add("mycolor", ColorCode.BLUE).build();
       assertNotNull(ctx.get("mycolor"));
    }

    @Test
    void assertDoesNotContainAnsiCode(){
        var ctx = StyleContext.builder().build();
        assertNull(ctx.get("mycolor"));
    }


    @Test
    void assertThrows_onNullMarkupName(){
        var builder = StyleContext.builder();
        assertThrows(NullPointerException.class, () -> builder.add(null, ColorCode.BLUE));
    }

    @Test
    void assertThrows_onNullAnsiCode(){
        var builder = StyleContext.builder();
        assertThrows(NullPointerException.class, () -> builder.add("mycolor", (AnsiCode) null));
    }

    @Test
    void assertThrows_onNullMapGiven(){
        var builder = StyleContext.builder();
        assertThrows(NullPointerException.class, () -> builder.add((Map<String, AnsiCode>) null));
    }

    @Test
    void assertContainsMapContent(){
        Map<String, AnsiCode> map = Map.of("mycolor", ColorCode.RED);
        var ctx = StyleContext.from(map);
        assertNotNull(ctx.get("mycolor"));
    }
}