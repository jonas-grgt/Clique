package io.github.kusoroadeolu.clique.internal.documentation;

import java.lang.annotation.*;


/**
 * Denotes types, constructors, and methods that are internal to Clique
 * and not intended for use by API consumers.
 *
 * <p>Internal APIs may change or be removed in any release without notice,
 * regardless of semantic versioning.
 *
 * <p>This annotation has {@link RetentionPolicy#CLASS CLASS} retention and
 * does not affect runtime behaviour.
 *
 * @since the version it was added
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented
public @interface InternalApi {
    String since() default "";
}
