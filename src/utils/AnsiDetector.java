package utils;

public final class AnsiDetector {

    private static final String TERM = "TERM";
    private static final String PLAIN = "plain";
    private static final String DUMB = "dumb";
    private static final String NO_COLOR = "NO_COLOR";
    private static final String CLIQUE_COLOR = "clique.color";
    private static final String ALWAYS = "always";
    private static final String NEVER = "never";



    private static boolean detectAnsi(){
        final String colors = System.getProperty(CLIQUE_COLOR);
        if(colors != null){
            if(colors.equalsIgnoreCase(ALWAYS)) return true;
            if(colors.equalsIgnoreCase(NEVER)) return false;
        }
        return ANSI_ENABLED;
    }

    private static final boolean ANSI_ENABLED = autoDetect();

    private static boolean autoDetect(){
        //Check if output is redirected
        if(System.console() == null) return false;

        //Check for no-color
        if(System.getenv(NO_COLOR) != null) return false;

        //Check the TERM var
        final String term = System.getenv(TERM);
        return term != null && !term.equalsIgnoreCase(DUMB) && !term.equalsIgnoreCase(PLAIN);
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
