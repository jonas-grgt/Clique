package io.github.kusoroadeolu.clique.core.utils;

import io.github.kusoroadeolu.clique.config.BorderSpec;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import java.util.Objects;

@InternalApi(since = "3.2.0")
public class MiscUtils {
    private MiscUtils(){throw new AssertionError();}

    public static void assertStyleNotNull(BorderSpec style){
        Objects.requireNonNull(style, "Border style cannot be null");
    }
}
