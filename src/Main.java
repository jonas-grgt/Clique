import core.clique.Clique;

public class Main {
    public static void main(String[] args) {
        Clique.parser().print("[blue, bold]Clique is awesome![/]");
        Clique.enableCliqueColors(false);
        Clique.parser().print("[blue, bold]Clique is awesome![/]");

    }
}
