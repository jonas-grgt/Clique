package io.github.kusoroadeolu.clique.core.utils;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;

public final class AnsiDetector {

    private AnsiDetector(){
        throw new AssertionError("cannot instantiate this");
    }

    private static boolean detectAnsi(){
        final String colors = System.getProperty(CLIQUE_COLOR);
        if (colors == null) return ANSI_ENABLED;
        else if(colors.equalsIgnoreCase(ALWAYS)) return true;
        else if(colors.equalsIgnoreCase(NEVER)) return false;
        else return ANSI_ENABLED;
    }

    private static final boolean ANSI_ENABLED = autoDetect();

    private static boolean autoDetect(){
        //Check for no-color
        if(System.getenv(NO_COLOR) != null) return false;

        //Check the TERM var
        final String term = System.getenv(TERM);

        if(term == null) {
            String os = System.getProperty(OS_NAME).toLowerCase();
            return os.contains(WIN);
        }

        return !term.equalsIgnoreCase(DUMB) && !term.equalsIgnoreCase(PLAIN);
    }



    public static boolean ansiEnabled(){
        return detectAnsi();
    }

    public static void enableCliqueColors(){
        System.setProperty(CLIQUE_COLOR, ALWAYS);
    }


    public static void disableCliqueColors(){
        System.setProperty(CLIQUE_COLOR, NEVER);
    }


}
