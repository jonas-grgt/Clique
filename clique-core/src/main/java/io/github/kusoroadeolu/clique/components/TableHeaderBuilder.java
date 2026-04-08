package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.internal.WidthAwareList;
import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;

import java.util.Collection;

import static io.github.kusoroadeolu.clique.internal.utils.StringUtils.parseToCellIfPresent;
import static io.github.kusoroadeolu.clique.internal.utils.TableUtils.handleNulls;
import static java.util.Objects.isNull;

@InternalApi(since = "3.2.0")
public class TableHeaderBuilder implements PendingTable{
        private final AbstractTable table;

        public TableHeaderBuilder(Table table) {
            this.table = (AbstractTable) table;
        }

        public Table headers(String... headers) {
            if (isNull(headers) || headers.length == 0)
                throw new IllegalArgumentException("Headers cannot be null or empty");

            final var rowList = new WidthAwareList();
            table.rows.add(rowList);

            for (int i = 0; i < headers.length; i++) {
                String header = headers[i];
                header = handleNulls(header, table.configuration.getNullReplacement());
                final var cell = parseToCellIfPresent(header, table.configuration.getParser());
                rowList.add(cell);
                final var colList = new WidthAwareList(); //To keep track of all values in this column
                colList.add(cell);
                table.columns.add(i, colList);
            }

            return table;
        }

        public Table headers(Collection<String> headers) {
            return this.headers(headers.toArray(String[]::new));
        }
    }