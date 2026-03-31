package io.github.kusoroadeolu.clique.core.utils;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;

@InternalApi(since = "3.2.0")
public class AnsiDetector {

    private AnsiDetector() {
        throw new AssertionError("Cannot instantiate this");
    }

    private final static AtomicBoolean ANSI_ENABLED = new AtomicBoolean(autoDetect());


    //FOR TESTS
    public static boolean testAnsiEnabled() {
        String cliqueColor = System.getProperty(CLIQUE_COLOR);
        if (ALWAYS.equals(cliqueColor)) return true;
        if (NEVER.equals(cliqueColor)) return false;
        return autoDetect();
    }

    public static boolean ansiEnabled() {
        return ANSI_ENABLED.get();
    }

    public static void enableCliqueColors() {
        System.setProperty(CLIQUE_COLOR, ALWAYS);
        ANSI_ENABLED.set(true);
    }

    public static void disableCliqueColors() {
        System.setProperty(CLIQUE_COLOR, NEVER);
        ANSI_ENABLED.set(false);
    }

    private static boolean autoDetect() {
        String noColor = System.getenv(NO_COLOR);
        if (noColor != null && !noColor.isEmpty()) return false;

        String cliColorForce = System.getenv(CLI_COLOR_FORCE);
        if (cliColorForce != null && !cliColorForce.isEmpty()) return true;

        String forceColor = System.getenv(FORCE_COLOR);
        if (forceColor != null && !forceColor.isEmpty()) return true;


        if (System.console() == null) {
            String os = System.getProperty(OS_NAME).toLowerCase();
            if (!os.contains(WIN)) return false;
        }

        String colorTerm = System.getenv(COLOR_TERM);
        if (colorTerm != null) return true;

        final String term = System.getenv(TERM);

        if (term == null) {
            if (System.getenv(WT_SESSION) != null) return true;
            String os = System.getProperty(OS_NAME).toLowerCase();
            return os.contains(WIN);
        }

        return !term.equalsIgnoreCase(DUMB) && !term.equalsIgnoreCase(PLAIN);
    }



}
