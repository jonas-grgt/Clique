package io.github.kusoroadeolu.clique.internal.themeloader;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CliqueThemesTest {

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
        List<CliqueTheme> themes = ThemeLoader.findAvailableThemes();
        System.out.println("Discovered themes count: " + themes.size());
        themes.forEach(t -> System.out.println("Theme: " + t.themeName()));
        assertNotNull(themes);
        assertTrue(themes.stream().anyMatch(t -> t.themeName().equals("test")));
    }

    @Test
    void testFindTheme() {
        Optional<CliqueTheme> theme = ThemeLoader.find("test");
        assertTrue(theme.isPresent());
        assertEquals("test", theme.get().themeName());
    }

    @Test
    void testFindNonExistentTheme() {
        Optional<CliqueTheme> theme = ThemeLoader.find("does-not-exist");
        assertFalse(theme.isPresent());
    }

    @Test
    void testThemeCaching() {
        // First call should discover
        Optional<CliqueTheme> theme1 = ThemeLoader.find("test");
        // Second call should use the map
        Optional<CliqueTheme> theme2 = ThemeLoader.find("test");

        assertTrue(theme1.isPresent());
        assertTrue(theme2.isPresent());
        assertSame(theme1.get(), theme2.get()); // Same instance from cache
    }

    @Test
    void testRegisterTheme() {
        ThemeLoader.register("test");

        // Verify the styles are registered in Clique
        String parsed = Clique.parser().parse("[test-red]Red[/]");
        assertNotNull(parsed);
        // Should contain ANSI codes, not the raw tag
        assertFalse(parsed.contains("[test-red]"));
    }


}
