package io.github.kusoroadeolu.clique.core.utils;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;

public final class AnsiDetector {

    private static boolean ANSI_ENABLED = autoDetect(); // mutable, not final

    public static boolean ansiEnabled() {
        return ANSI_ENABLED; // just return the field
    }

    public static void enableCliqueColors() {
        System.setProperty(CLIQUE_COLOR, ALWAYS);
        ANSI_ENABLED = true; // update cache directly
    }

    public static void disableCliqueColors() {
        System.setProperty(CLIQUE_COLOR, NEVER);
        ANSI_ENABLED = false;
    }

    private AnsiDetector() {
        throw new AssertionError("cannot instantiate this");
    }

    private static boolean autoDetect() {
        String noColor = System.getenv(NO_COLOR);
        if (noColor != null && !noColor.isEmpty()) return false;

        String cliColorForce = System.getenv(CLI_COLOR_FORCE);
        if (cliColorForce != null && !cliColorForce.isEmpty()) return true;

        if (System.console() == null) return false;

        String colorTerm = System.getenv(COLOR_TERM);
        if (colorTerm != null) return true;

        final String term = System.getenv(TERM);
        if (term == null) {
            // Windows Terminal sets WT_SESSION
            if (System.getenv(WT_SESSION) != null) return true;
            String os = System.getProperty(OS_NAME).toLowerCase();
            return os.contains(WIN);
        }

        return !term.equalsIgnoreCase(DUMB) && !term.equalsIgnoreCase(PLAIN);
    }
}
