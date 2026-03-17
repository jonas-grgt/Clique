package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class BoxFactory {
    private BoxFactory() {
        throw new AssertionError();
    }

    public static BoxDimensionBuilder getBoxDimensionBuilder(BoxType type, BoxConfiguration config) {
        validateTypeAndConfig(type, config);
        var borderChar = BorderChars.from(type);
        Box box = new DefaultBox(borderChar, config);
        return new BoxDimensionBuilder(box);
    }

    public static BoxDimensionBuilder getBoxDimensionBuilder(BoxType type) {
        return getBoxDimensionBuilder(type, BoxConfiguration.DEFAULT);
    }

    public static CustomizableBoxDimensionBuilder getCustomizableBoxDimensionBuilder(BoxType type) {
        return getCustomizableBoxDimensionBuilder(type, BoxConfiguration.DEFAULT);
    }

    public static CustomizableBoxDimensionBuilder getCustomizableBoxDimensionBuilder(BoxType type, BoxConfiguration config) {
        validateTypeAndConfig(type, config);
        var customizable = (CustomizableBox) getBoxDimensionBuilder(type, config);
        return new CustomizableBoxDimensionBuilder(customizable);
    }

    static void validateTypeAndConfig(BoxType type, BoxConfiguration config) {
        requireNonNull(type, "Box type cannot be null");
        requireNonNull(config, "Box configuration cannot be null");
    }


}
