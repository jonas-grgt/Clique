package io.github.kusoroadeolu.clique.components;


import io.github.kusoroadeolu.clique.internal.documentation.Stable;

import java.util.Collection;

/**
 * @since 1.0.0
 * */
@Stable(since = "3.0.0")
public interface Table extends Component {
    Table row(String... rows);

    Table row(Collection<String> rows);

    Table removeRow(int index);

    Table removeCell(int row, int col);

    Table updateCell(int row, int col, String text);
}
