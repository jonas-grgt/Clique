package tables.interfaces;

public interface Customizable extends Table{
    Customizable customizeEdge(char edge);
    Customizable customizeVerticalLine(char vLine);
    Customizable customizeHorizontalLine(char hLine);
}
