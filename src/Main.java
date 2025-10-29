import parser.CliqueParser;

public class Main {
    public static void main(String[] args) {
        CliqueParser parser = new CliqueParser();

        // test runner helper
        runTest(parser, "[red,]Hello", "should handle trailing comma gracefully");
        runTest(parser, "[ red , bold ]Hello[/]", "should apply red+bold then reset");
        runTest(parser, "[/]Hello", "should ignore standalone reset");
        runTest(parser, "[rainbow]Hello", "should print literal tag since rainbow doesn’t exist");
        runTest(parser, "[red]Hello[/][green]World[/]", "should print red Hello and green World");
        runTest(parser, "[red]Hello [blue]World[/][/]", "should apply red then blue then reset");
        runTest(parser, "Hello[]World", "should ignore empty tag and print raw");
        runTest(parser, "[redHello", "should ignore malformed unclosed tag");
        runTest(parser, "[[[[[red]]]]]Hello", "should treat double brackets as literal text");
        runTest(parser, "[bold, blue, ul]Hello[/] [bold, red, ul]World", "should style both words correctly");
    }

    private static void runTest(CliqueParser parser, String input, String description) {
        System.out.println("\n🧪 Test: " + description);
        System.out.println("Input: " + input);
        System.out.println("Output:");
        System.out.println(parser.parse(input));
        System.out.println("--------------------------------------------------");
    }
}
