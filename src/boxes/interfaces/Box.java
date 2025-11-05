package boxes.interfaces;

import boxes.abstractboxes.AbstractBox;
import boxes.configuration.BoxConfiguration;

public interface Box {
    Box configuration(BoxConfiguration boxConfiguration);

    Box width(int width);

    Box content(String content);

    Box length(int length);

    String buildBox();

    void render();
}
