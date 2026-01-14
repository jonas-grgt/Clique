package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.boxes.Box;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.tables.Table;
import com.github.kusoroadeolu.clique.tables.TableType;

public class Main {
    public static void main(String[] args)  {
        BoxConfiguration c = BoxConfiguration.immutableBuilder().autoSize(true).parser(Clique.parser()).build();
        String s = "Hello \uD83D\uDE00";
        Clique.table(TableType.BOX_DRAW).addHeaders("123").addRows(s).render();


    }


}


