package io.github.kusoroadeolu.clique.internal.utils;

import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.kusoroadeolu.clique.internal.Constants.*;

@InternalApi(since = "3.2.0")
public class AnsiDetector {

    private AnsiDetector() {}

    private static volatile boolean ANSI_ENABLED = autoDetect();

    public static void refresh() {
        ANSI_ENABLED = autoDetect();
    }

    public static boolean ansiEnabled() {
        return ANSI_ENABLED;
    }

    public static void enableCliqueColors() {
        System.setProperty(CLIQUE_COLOR, ALWAYS);
        ANSI_ENABLED = true;
    }

    public static void disableCliqueColors() {
        System.setProperty(CLIQUE_COLOR, NEVER);
        ANSI_ENABLED = false;
    }

    private static boolean autoDetect() {
        String noColor = System.getenv(NO_COLOR);
        if (noColor != null && !noColor.isEmpty()) return false;

        String cliqueColor = System.getProperty(CLIQUE_COLOR);
        if (ALWAYS.equals(cliqueColor)) return true;
        if (NEVER.equals(cliqueColor)) return false;

        String cliColorForce = System.getenv(CLI_COLOR_FORCE);
        if (cliColorForce != null && !cliColorForce.isEmpty() && !cliColorForce.equals("0")) return true;

        String forceColor = System.getenv(FORCE_COLOR);
        if (forceColor != null && !forceColor.isEmpty()) return true;

        String colorTerm = System.getenv(COLOR_TERM);
        if (colorTerm != null) return true;

        final String term = System.getenv(TERM);
        if (term != null && !term.equalsIgnoreCase(DUMB) && !term.equalsIgnoreCase(PLAIN)) return true;

        String os = System.getProperty(OS_NAME, EMPTY).toLowerCase();
        if (System.console() == null) {
            if (!os.contains(WIN)) return false;
        }

        if (System.getenv(WT_SESSION) != null) return true;
        else return os.contains(WIN);
    }



}
