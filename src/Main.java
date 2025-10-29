import core.clique.Clique;

public class Main {
    public static void main(String[] args) {
        Clique.ofDynamic()
                .enableStrictParsing()
                .print("[magenta, bold]Hello [*red]World");
    }


}
