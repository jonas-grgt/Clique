package io.github.kusoroadeolu.clique.core.display;


/**
 * @deprecated in favor of {@link io.github.kusoroadeolu.clique.config.BorderStyle} customization methods. This will be removed
 * */
@Deprecated(since = "3.1", forRemoval = true)
public interface Customizable<T extends Customizable<T>> {
    T customizeEdge(char edge);

    T customizeVerticalLine(char vLine);

    T customizeHorizontalLine(char hLine);
}
