package io.github.kusoroadeolu.clique.testsupport;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestThemeTest {
    @Test
    void testServiceFileExists() throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        var url = cl.getResource("META-INF/services/io.github.kusoroadeolu.clique.spi.CliqueTheme");        assertNotNull(url, "ServiceLoader file not found on classpath");

        try (var in = url.openStream()) {
            String content = new String(in.readAllBytes());
            System.out.println("Service file content: '" + content + "'");
            assertTrue(content.contains("TestTheme"));
        }
    }

    @Test
    void testDiscoverThemes() {
        List<CliqueTheme> themes = Clique.findAvailableThemes();
        themes.forEach(t -> System.out.println("Theme: " + t.themeName()));
        assertTrue(themes.stream().anyMatch(t -> t.themeName().equals("test")));
    }

    @Test
    void testFindTheme() {
        Optional<CliqueTheme> theme = Clique.findTheme("test");
        assertTrue(theme.isPresent());
        assertEquals("test", theme.get().themeName());
    }

    @Test
    void testFindNonExistentTheme() {
        Optional<CliqueTheme> theme = Clique.findTheme("does-not-exist");
        assertFalse(theme.isPresent());
    }

    @Test
    void testThemeCaching() {
        // First call should discover
        Optional<CliqueTheme> theme1 = Clique.findTheme("test");
        // Second call should use the map
        Optional<CliqueTheme> theme2 = Clique.findTheme("test");

        assertTrue(theme1.isPresent());
        assertTrue(theme2.isPresent());
        assertSame(theme1.get(), theme2.get()); // Same instance from cache
    }

    @Test
    void testRegisterTheme() {
        Clique.registerTheme("test");

        // Verify the styles are registered in Clique
        String parsed = Clique.parser().parse("[test-red]Red[/]");
        assertNotNull(parsed);
        // Should contain ANSI codes, not the raw tag
        assertFalse(parsed.contains("[test-red]"));
    }


}