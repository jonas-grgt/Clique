
# CLIQUE - README [UNRELEASED]
## INTRODUCTION 
Clique is my mini CLI framework aimed at beautifying CLI applications in Java

### IMPLEMENTATION
Clique currently uses a fluent builder pattern to chain styled strings with each other.
</br> You can append styles to a string using `append` and print it to the terminal. This does not reset the terminal style
```java
        Clique.printer()
                .append("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
                .append("World", ColorCode.RED, StyleCode.DIM , StyleCode.REVERSE_VIDEO)
                .print();    
```


You can append styles to strings and reset the terminal style using `appendAndReset` . I added this for more flexibility though it might be a mistake in the long run
```java
       Clique.printer()
                .appendAndReset("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
                .appendAndReset("World", ColorCode.RED, StyleCode.DIM , StyleCode.REVERSE_VIDEO)
                .print();    
```


You can apply styles to text with `apply`. After applying styles to a string the terminal is always rest
```java
    String styledText = Clique.printer().apply("I am a red text", ColorCode.RED);
```

You can get the styledText after appending strings by using the `get` method
```java
      String styledText = Clique.printer()
                .appendAndReset("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
                .appendAndReset("World", ColorCode.RED, StyleCode.DIM , StyleCode.REVERSE_VIDEO)
                .get(); 
```

### Planned features
- Markdown based parsing 
```java
CliqueParser parser = new CliqueParser(StyleCode.UNDERLINE); //Style code that will apply to all the text parsed with the parser
parser.print("[red, ul]Hello[green dim]World[/]") //Should output a Hello World fully underlined with their respective colors
```
- Tables
- Interactive options
- Full terminal support for all OS's
