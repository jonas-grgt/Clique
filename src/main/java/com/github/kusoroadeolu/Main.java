package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.boxes.Box;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.tables.Table;
import com.github.kusoroadeolu.clique.tables.TableType;
import com.github.kusoroadeolu.clique.tables.structures.Cell;

public class Main {
    public static void main(String[] args)  {
        Clique.table(TableType.MARKDOWN).addHeaders("123").addRows("Hello X").render();
        System.out.println();
        Clique.table(TableType.MARKDOWN).addHeaders("123").addRows("Hello 😀").render();

    }


}


