import tables.factory.TableFactory;
import tables.factory.TableType;

public class Main {
    public static void main(String[] args) {
        TableFactory.getTable(TableType.COMPACT)
                .addHeaders("Name", "Age", "Class")
                .addRows("John", "25", "Class A")
                .addRows("Doe", "26", "Class B")
                .render();
    }


}
