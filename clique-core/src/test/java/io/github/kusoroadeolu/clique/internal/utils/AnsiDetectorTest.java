package io.github.kusoroadeolu.clique.internal.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import static io.github.kusoroadeolu.clique.internal.Constants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SystemStubsExtension.class)
class AnsiDetectorTest {

    @SystemStub
    private EnvironmentVariables envVars;


    @BeforeEach
    void clearProperties() {
        System.clearProperty(CLIQUE_COLOR);
    }

    @Test
    void noColor_nonEmpty_disablesAnsi() {
        envVars.set(NO_COLOR, "1");
        assertFalse(AnsiDetector.testAnsiEnabled());
    }

    @Test
    void noColor_empty_doesNotDisableAnsi() {
        envVars.set(NO_COLOR, "");
        envVars.set(COLOR_TERM, "truecolor");
        assertTrue(AnsiDetector.testAnsiEnabled());
    }


    @Test
    void cliColorForce_set_enablesAnsi() {
        envVars.set(CLI_COLOR_FORCE, "1");
        assertTrue(AnsiDetector.testAnsiEnabled());
    }


    @Test
    void colorterm_set_enablesAnsi() {
        envVars.set(COLOR_TERM, "truecolor");
        assertTrue(AnsiDetector.testAnsiEnabled());
    }


    @Test
    void term_dumb_disablesAnsi() {
        envVars.set(TERM, "dumb");
        assertFalse(AnsiDetector.testAnsiEnabled());
    }

    @Test
    void term_plain_disablesAnsi() {
        envVars.set(TERM, "plain");
        assertFalse(AnsiDetector.testAnsiEnabled());
    }

    @Test
    void term_xterm_enablesAnsi() {
        envVars.set(TERM, "xterm-256color");
        assertTrue(AnsiDetector.testAnsiEnabled());
    }

    // --- Toggle methods ---

    @Test
    void enableCliqueColors_updatesCache() {
        AnsiDetector.disableCliqueColors();
        assertFalse(AnsiDetector.testAnsiEnabled());
        AnsiDetector.enableCliqueColors();
        assertTrue(AnsiDetector.testAnsiEnabled());
    }

    @Test
    void disableCliqueColors_updatesCache() {
        AnsiDetector.enableCliqueColors();
        assertTrue(AnsiDetector.testAnsiEnabled());
        AnsiDetector.disableCliqueColors();
        assertFalse(AnsiDetector.testAnsiEnabled());
    }

}