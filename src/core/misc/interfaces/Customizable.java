package core.misc.interfaces;

import tables.interfaces.Table;

public interface Customizable<T extends Customizable<T>> {
    T customizeEdge(char edge);
    T customizeVerticalLine(char vLine);
    T customizeHorizontalLine(char hLine);
}
