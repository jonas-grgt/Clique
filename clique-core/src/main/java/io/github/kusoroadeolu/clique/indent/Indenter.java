package io.github.kusoroadeolu.clique.indent;


import io.github.kusoroadeolu.clique.core.display.Borderless;
import io.github.kusoroadeolu.clique.core.documentation.Stable;

import java.util.Collection;

/**
 * @since 1.1.0
 * */
@Stable(since = "3.1.3")
public interface Indenter extends Borderless {

    Indenter indent(int level, String flag);

    Indenter indent(int level);

    Indenter indent(int level, Flag flag);

    Indenter indent(String flag);

    Indenter indent(Flag flag);

    Indenter indent();

    Indenter add(String... arr);

    Indenter add(Collection<String> list);

    Indenter add(String str);

    Indenter add(Object object);

    Indenter unindent();

    String get();

    void flush();

    Indenter resetLevel();
}
