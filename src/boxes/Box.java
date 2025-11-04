package boxes;

import boxes.abstractboxes.AbstractBox;
import boxes.configuration.BoxConfiguration;

public interface Box {
    AbstractBox configuration(BoxConfiguration boxConfiguration);

    AbstractBox width(int width);

    AbstractBox content(String content);

    AbstractBox length(int length);
}
