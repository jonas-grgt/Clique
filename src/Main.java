import core.ansi.enums.ColorCode;
import core.clique.Clique;
import tables.Table;
import tables.configuration.TableBorderStyle;
import tables.configuration.TableConfiguration;
import tables.factory.TableType;

public class Main {
    public static void main(String[] args) {
        TableConfiguration configuration = TableConfiguration
                .builder()
                .tableBorderStyle(TableBorderStyle.builder()
                        .horizontalBorderStyles(ColorCode.CYAN)
                        .verticalBorderStyles(ColorCode.MAGENTA)
                        .edgeBorderStyles(ColorCode.YELLOW))
                .parser(Clique.parser());

        Table t = Clique.table(TableType.DEFAULT, configuration)
                .addHeaders("[green, bold]Name[/]", "[green, bold]Age[/]", "[green, bold]Class[/]")
                .addRows("[red]John[/]", "25", "Class A")
                .addRows("[red]Doe[/]", "26", "Class B");
        t.render();

    }


}
