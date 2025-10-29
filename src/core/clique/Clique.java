package core.clique;

import core.style.builder.StyleBuilder;
import core.style.builder.StyleBuilderImpl;

/**
 * A factory class to hide the instantiation of the style builder
 * */
public final class Clique {
    public static StyleBuilder printer(){
        return new StyleBuilderImpl();
    }
}
