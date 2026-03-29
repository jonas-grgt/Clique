package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.documentation.Stable;

/**
 * @since 3.1.0
 * */
@Stable(since = "3.1.3")public interface Frame extends Bordered{
    DefaultFrame title(String title, FrameAlign titleAlign);

    DefaultFrame title(String title);

    DefaultFrame width(int width);

    DefaultFrame nest(String str);

    DefaultFrame nest(String str, FrameAlign align);

    DefaultFrame nest(Component component);

    DefaultFrame nest(Component component, FrameAlign align);
}
