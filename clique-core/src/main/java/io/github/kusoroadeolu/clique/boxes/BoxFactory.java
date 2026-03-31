package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.boxes.AbstractBox.BoxDimensionBuilder;
import io.github.kusoroadeolu.clique.boxes.AbstractBox.CustomizableBoxDimensionBuilder;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;

import static java.util.Objects.requireNonNull;

@InternalApi(since = "3.2.0")
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

    /**
     * @deprecated in favor of {@link io.github.kusoroadeolu.clique.config.BorderStyle} customization methods. This will be removed
     * */
    @Deprecated(since = "3.1", forRemoval = true)
    public static CustomizableBoxDimensionBuilder getCustomizableBoxDimensionBuilder(BoxType type) {
        return getCustomizableBoxDimensionBuilder(type, BoxConfiguration.DEFAULT);
    }

    /**
     * @deprecated in favor of {@link io.github.kusoroadeolu.clique.config.BorderStyle} customization methods. This will be removed
     * */
    @Deprecated(since = "3.1", forRemoval = true)
    public static CustomizableBoxDimensionBuilder getCustomizableBoxDimensionBuilder(BoxType type, BoxConfiguration config) {
        validateTypeAndConfig(type, config);
        var borderChar = BorderChars.from(type);
        Box box = new DefaultBox(borderChar, config);
        return new CustomizableBoxDimensionBuilder(box);
    }

    static void validateTypeAndConfig(BoxType type, BoxConfiguration config) {
        requireNonNull(type, "Box type cannot be null");
        requireNonNull(config, "Box configuration cannot be null");
    }


}
