import boxes.concrete.DoubleLineBox;
import boxes.configuration.BoxConfiguration;
import core.ansi.enums.ColorCode;
import core.misc.BorderStyle;

public class Main {
    public static void main(String[] args) {
        //Width length, content
        BorderStyle style = BorderStyle.builder()
                .horizontalBorderStyles(ColorCode.CYAN)
                .verticalBorderStyles(ColorCode.MAGENTA)
                .edgeBorderStyles(ColorCode.YELLOW);

        DoubleLineBox box = new DoubleLineBox(0, 10, "[red] Disaster is a very[blue] long word");
        box.configuration(BoxConfiguration.builder().borderStyle(style).autoAdjustBox(true));
        System.out.println(box.buildBox());
    }

}


