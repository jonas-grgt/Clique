package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.configuration.ItemListConfiguration;
import io.github.kusoroadeolu.clique.internal.ListItem;
import io.github.kusoroadeolu.clique.internal.documentation.Experimental;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.internal.Constants.*;
import static io.github.kusoroadeolu.clique.style.StyleCode.RESET;

@Experimental(since = "4.0.0")
public class ItemList implements Component {
    private final List<ListItem> items;
    private ItemListConfiguration configuration;
    private final static ItemList NONE = new ItemList();

    public ItemList(ItemListConfiguration configuration) {
        this.items = new ArrayList<>();
        this.configuration = configuration;
    }

    public ItemList() {
        this(ItemListConfiguration.DEFAULT);
    }

    public ItemList item(String symbol, String content, ItemList itemList){
        Objects.requireNonNull(itemList, "Sublist cannot be null");
        assertNotSelf(itemList);
        this.items.add(new ListItem(symbol, content, itemList.setConfiguration(this.configuration)));
        return this;
    }

    public ItemList item(String symbol, String content){
        this.items.add(new ListItem(symbol, content, NONE));
        return this;
    }

    @Override
    public String get() {
        StringBuilder sb = new StringBuilder();
        buildList(sb, ZERO ,this);
        return parseString(sb.toString());
    }

    void assertNotSelf(ItemList list){
        if (list == this) throw new IllegalArgumentException("Cannot nest list within itself");
    }

    void buildList(StringBuilder sb, int depth, ItemList root) {
        for (ListItem item : root.items) {
            sb.repeat(BLANK, depth * configuration.getIndentSize())
                    .append(item.symbol())
                    .repeat(BLANK, configuration.getSymbolSpacing())
                    .append(item.content())
                    .append(RESET)
                    .append(NEWLINE);

            if (item.sublist() != NONE) {
                buildList(sb, depth + 1, item.sublist());
            }
        }
    }


    //Mutable setter, for allowing parents to pass configs to children
    ItemList setConfiguration(ItemListConfiguration configuration){
        this.configuration = configuration;
        return this;
    }

    private String parseString(String str) {
        return StringUtils.parseIfPresent(str, this.configuration.getParser());
    }

    //FOR TESTS
    ItemList child(){
        return items.stream().map(ListItem::sublist)
                .toList()
                .getFirst();
    }

    ItemListConfiguration configuration(){
        return configuration;
    }

}
