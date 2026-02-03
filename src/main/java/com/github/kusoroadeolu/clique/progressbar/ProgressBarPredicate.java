package com.github.kusoroadeolu.clique.progressbar;

import java.util.function.Predicate;

public record ProgressBarPredicate(Predicate<Integer> predicate, String format) {
    public boolean matches(final int val){
        return predicate.test(val);
    }
}
