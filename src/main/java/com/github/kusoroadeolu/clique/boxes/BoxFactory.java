package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import com.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import com.github.kusoroadeolu.clique.config.BoxConfiguration;

public class BoxFactory {
    private BoxFactory(){
        throw new AssertionError();
    }

    public static BoxDimensionBuilder getBoxDimensionBuilder(BoxType type, BoxConfiguration config){
        var box = switch (type){
            case DEFAULT -> new DefaultBox(config);
            case CLASSIC -> new ClassicBox(config);
            case ROUNDED -> new RoundedBox(config);
            case DOUBLE_LINE -> new DoubleLineBox(config);
        };
        return new BoxDimensionBuilder(box);
    }

    public static BoxDimensionBuilder getBoxDimensionBuilder(BoxType type){
        return getBoxDimensionBuilder(type, BoxConfiguration.DEFAULT);
    }

    public static CustomizableBoxDimensionBuilder getCustomizableBoxDimensionBuilder(BoxType type){
        return getCustomizableBoxDimensionBuilder(type, BoxConfiguration.DEFAULT);
    }

    public static CustomizableBoxDimensionBuilder getCustomizableBoxDimensionBuilder(BoxType type, BoxConfiguration config){
        var customizable = switch (type){
            case DEFAULT -> (CustomizableBox) getBoxDimensionBuilder(type, config);
            default -> throw new UnsupportedOperationException("Box type: %s is not customizable".formatted(type));
        };

        return new CustomizableBoxDimensionBuilder(customizable);
    }




}
