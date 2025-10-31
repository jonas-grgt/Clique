package tables.structures;

public class Cell {
    private String text;
    private String styledText;

    public Cell(String text, String styled) {
        this.text = text;
        this.styledText = styled;
    }

    public String styledText() {
        return styledText;
    }

    public Cell setStyledText(String styledText) {
        this.styledText = styledText;
        return this;
    }

    public String text() {
        return text;
    }

    public Cell setText(String text) {
        this.text = text;
        return this;
    }
}
