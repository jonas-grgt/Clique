package io.github.kusoroadeolu.clique.core.documentation;

import java.lang.annotation.*;

/**
 * Marks a public API as experimental. Stronger signal than {@link Unstable}
 * this API exists for early feedback and may be removed entirely if the
 * approach does not pan out.
 *
 * <p>If you use an experimental API, consider submitting feedback on
 * whether it meets your needs.
 *
 * <p>This annotation has {@link RetentionPolicy#CLASS CLASS} retention and
 * does not affect runtime behaviour.
 *
 * @since the version it was added
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented
public @interface Experimental {
    String since() default "";
    String reason() default "";
}