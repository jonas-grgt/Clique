import boxes.enums.BoxType;
import boxes.interfaces.Box;
import core.clique.Clique;
import tables.factory.TableType;
import tables.interfaces.CustomizableTable;
import tables.interfaces.Table;

public class Main {
    public static void main(String[] args) {
        Box b = Clique.customizableBox(BoxType.DEFAULT)
                .customizeEdge('<')
                .customizeVerticalLine('~')
                .length(10)
                .width(10)
                .content("[red]This is my custom box :)");
    }

}


