package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.documentation.Stable;

/**
 * @since 3.1.0
 * */
@Stable(since = "3.2.0")
public interface Frame extends Bordered{
    Frame title(String title, FrameAlign titleAlign);

    Frame title(String title);

    Frame width(int width);

    Frame nest(String str);

    Frame nest(String str, FrameAlign align);

    Frame nest(Component component);

    Frame nest(Component component, FrameAlign align);
}
