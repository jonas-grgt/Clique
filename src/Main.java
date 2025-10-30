import tables.AbstractTable;
import tables.DefaultTable;
import tables.MinimalTable;
import tables.Table;

public class Main {
    public static void main(String[] args) {
//        Clique.ofDynamic()
//                .enableStrictParsing()
//                .print("[magenta, bold]Hello [*red]World");

        MinimalTable t = new MinimalTable();
        t.addHeaders("Name", "Age", "Young")
                .addRows("Vic", "1", "True")
                .addRows("Kush", "2", "True")
                .render();


    }


}
