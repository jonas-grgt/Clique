package io.github.kusoroadeolu.clique.progressbar;

import io.github.kusoroadeolu.clique.core.documentation.Stable;

import java.util.function.Predicate;

/**
 * @since 3.0.0
 * */
@Stable(since = "3.1.3")
public record ProgressBarPredicate(Predicate<Integer> predicate, String format) {
    public boolean matches(final int val) {
        return predicate.test(val);
    }
}
