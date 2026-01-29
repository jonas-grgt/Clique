package com.github.kusoroadeolu.clique.indent;

import com.github.kusoroadeolu.clique.config.IndenterConfiguration;
import com.github.kusoroadeolu.clique.core.display.Printable;

import java.io.PrintStream;
import java.util.Collection;

public interface Indenter extends Printable {
    @Deprecated(since = "1.2.1")
    Indenter configuration(IndenterConfiguration configuration);

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

    void print(PrintStream stream);

    String get();

    void clear();

    void flush();

    String parseString(String str);

    Indenter resetLevel();
}
