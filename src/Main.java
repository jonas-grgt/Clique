import core.clique.Clique;
import tables.configuration.CellAlign;
import tables.configuration.TableConfiguration;
import tables.factory.TableType;
import tables.interfaces.Table;

public class Main {
    public static void main(String[] args) {
        Table table = Clique.table(TableType.DEFAULT, TableConfiguration.builder().columnAlignment(0, CellAlign.RIGHT));

        // 2. Add headers for the columns
        table.addHeaders("Rank", "Language", "Usage")
                .addRows("1", "Java", "Enterprise Apps")
                .addRows("2", "Python", "Data Science/ML")
                .addRows("3", "JavaScript", "Web Development");
        // 3. Render the table to the console
        System.out.println("--- Rendering Default Table ---");
        table.render();
    }

}


