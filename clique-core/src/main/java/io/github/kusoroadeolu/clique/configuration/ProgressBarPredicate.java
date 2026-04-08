package io.github.kusoroadeolu.clique.configuration;

import io.github.kusoroadeolu.clique.internal.documentation.Stable;

import java.util.function.Predicate;

/**
 * @since 3.0.0
 * */
@Stable(since = "3.2.0")
public record ProgressBarPredicate(Predicate<Integer> predicate, String format) {
    public boolean matches(final int val) {
        return predicate.test(val);
    }
}
