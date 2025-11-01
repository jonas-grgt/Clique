import core.ansi.enums.ColorCode;
import core.clique.Clique;
import tables.configuration.TableBorderStyle;
import tables.configuration.TableConfiguration;
import tables.factory.TableType;

public class Main {
    public static void main(String[] args) {
        TableConfiguration configuration = TableConfiguration
                .builder()
                .tableBorderStyle(
                        TableBorderStyle.builder()
                        .horizontalBorderStyles(ColorCode.CYAN)
                        .verticalBorderStyles(ColorCode.BLUE)
                        .edgeBorderStyles(ColorCode.YELLOW))
                .parser(Clique.parser());

        Clique.customizableTable(TableType.DEFAULT, configuration)
                .customizeVerticalLine(':')
                .customizeHorizontalLine('~')
                .customizeEdge('*')
                .addHeaders("[green, bold]Name[/]", "[green, bold]Age[/]", "[green, bold]Class[/]")
                .addRows("[red]John[/]", "25", "Class A")
                .addRows("[red]Doe[/]", "26", "Class B")
                .render();


    }


}
