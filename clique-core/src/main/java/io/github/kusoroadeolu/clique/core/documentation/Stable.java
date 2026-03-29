package io.github.kusoroadeolu.clique.core.documentation;

import java.lang.annotation.*;

/**
 * Marks a public API as stable and safe to depend on across minor releases.
 *
 * <p>Breaking changes to a stable API will not be made without a major version
 * bump and a prior deprecation cycle.
 *
 * <p>This annotation has {@link RetentionPolicy#CLASS CLASS} retention and
 * does not affect runtime behaviour.
 *
 * @since the version it was added
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented
public @interface Stable {
    String since() default "";
}