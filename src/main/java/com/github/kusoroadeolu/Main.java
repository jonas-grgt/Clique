package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.boxes.Box;
import com.github.kusoroadeolu.clique.boxes.BoxType;
import com.github.kusoroadeolu.clique.config.BoxConfiguration;

public class Main {
    public static void main(String[] args)  {
        BoxConfiguration c = BoxConfiguration.immutableBuilder().autoSize(true).build();
        String s = "Title\n\nThis is the first paragraph with some text.\nSecond line is even longer than the first one.\nShort.\n\nAnother paragraph here.";
        Clique.box(BoxType.CLASSIC, c)
                .content(s)
                .render();

    }


}


