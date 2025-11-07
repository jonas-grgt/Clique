package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;

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
