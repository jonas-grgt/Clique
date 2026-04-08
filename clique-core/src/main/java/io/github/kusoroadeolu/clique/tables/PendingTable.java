package io.github.kusoroadeolu.clique.tables;

import java.util.Collection;

public interface PendingTable {
    Table headers(String... headers);

    Table headers(Collection<String> headers);
}
