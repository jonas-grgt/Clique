package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadeolu.clique.ansi.ColorCode;
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
        assertThrows(NullPointerException.class, () -> StyleContext.builder().add(null, ColorCode.BLUE).build());
    }

    @Test
    void assertThrows_onNullAnsiCode(){
        assertThrows(NullPointerException.class, () -> StyleContext.builder().add("mycolor", (AnsiCode) null).build());
    }

    @Test
    void assertThrows_onNullMapGiven(){
        assertThrows(NullPointerException.class, () -> StyleContext.builder().add((Map<String, AnsiCode>) null).build());
    }

    @Test
    void assertContainsMapContent(){
        Map<String, AnsiCode> map = Map.of("mycolor", ColorCode.RED);
        var ctx = StyleContext.from(map);
        assertNotNull(ctx.get("mycolor"));
    }
}