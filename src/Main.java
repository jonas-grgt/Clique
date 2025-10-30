import core.clique.Clique;
import tables.Table;

public class Main {
    public static void main(String[] args) {
//        Clique.ofDynamic()
//                .enableStrictParsing()
//                .print("[magenta, bold]Hello [*red]World");

        Table t = new Table();
        t.addHeaders("Name", "Age", "Young")
                .addRows("Vic", "1", "True")
                .addRows("Kush", "2", "True")
                .render();


    }


}
