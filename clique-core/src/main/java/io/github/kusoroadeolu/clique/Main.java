package io.github.kusoroadeolu.clique;

import io.github.kusoroadeolu.clique.tables.TableType;

public class Main {
    public static void main(String[] args) {
        var table = Clique.table(TableType.ROUNDED_BOX_DRAW)
                .addHeaders("Name", "Score")
                .addRows("👨‍👦Fire", "100")
                .addRows("Normal", "200");
        String result = table.get();

        System.out.println(result);
    }
}
