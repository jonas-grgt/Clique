package io.github.kusoroadeolu.clique.core.documentation;

import java.lang.annotation.*;

/**
 * Marks a public API as intentionally unstable. The API is available for use
 * but may change shape between minor versions without a deprecation cycle.
 *
 * <p>This annotation has {@link RetentionPolicy#CLASS CLASS} retention and
 * does not affect runtime behaviour.
 *
 * @since the version it was added
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented
public @interface Unstable {
    String since() default "";
    String reason() default "";
}