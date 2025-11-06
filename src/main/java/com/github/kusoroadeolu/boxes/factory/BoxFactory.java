package com.github.kusoroadeolu.boxes.factory;

import com.github.kusoroadeolu.boxes.concrete.ClassicBox;
import com.github.kusoroadeolu.boxes.concrete.DefaultBox;
import com.github.kusoroadeolu.boxes.concrete.DoubleLineBox;
import com.github.kusoroadeolu.boxes.concrete.RoundedBox;
import com.github.kusoroadeolu.boxes.configuration.BoxConfiguration;
import com.github.kusoroadeolu.boxes.interfaces.Box;
import com.github.kusoroadeolu.boxes.interfaces.CustomizableBox;

public class BoxFactory {
    private BoxFactory(){

    }

    public static Box getBox(BoxType type, BoxConfiguration config){
        return switch (type){
            case DEFAULT -> new DefaultBox(config);
            case CLASSIC -> new ClassicBox(config);
            case ROUNDED -> new RoundedBox(config);
            case DOUBLE_LINE -> new DoubleLineBox(config);
            case null -> throw new IllegalArgumentException("BoxType cannot be null");
        };
    }

    public static Box getBox(BoxType type){
        return getBox(type, BoxConfiguration.builder());
    }

    public static CustomizableBox getCustomizableBox(BoxType type){
        return getCustomizableBox(type, BoxConfiguration.builder());
    }

    public static CustomizableBox getCustomizableBox(BoxType type, BoxConfiguration config){
        return switch (type){
            case DEFAULT -> (CustomizableBox) getBox(type, config);
            default -> throw new UnsupportedOperationException(type + " is not customizable");
        };
    }




}
