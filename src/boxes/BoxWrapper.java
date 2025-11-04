package boxes;

import boxes.configuration.BoxConfiguration;
import core.misc.Cell;

import java.util.List;

public record BoxWrapper(
        int width,
        int length,
        BoxConfiguration boxConfiguration,
        List<Cell> wordWrap,
        String hLine,
        String vLine,
        String tLeft,
        String tRight,
        String bRight,
        String bLeft
) {
}
