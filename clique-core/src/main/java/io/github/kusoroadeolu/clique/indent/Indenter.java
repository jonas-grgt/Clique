package io.github.kusoroadeolu.clique.indent;


import io.github.kusoroadeolu.clique.core.display.Accumulated;

import java.util.Collection;

public interface Indenter extends Accumulated {

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
