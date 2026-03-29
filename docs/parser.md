# Parser

Clique's parser allows you to use a simple markup format for styling text instead of verbose `styleBuilder` calls or raw ANSI codes.

## Basic Usage
### Parse and Print
```java
// Parse and print directly
Clique.parser().print("[red, bold]Error:[/] Something went wrong");

// Parse and return styled string
String styled = Clique.parser().parse("[red, bold]Error:[/] Something went wrong");
System.out.println(styled);
```
![Ansi Output](../images/ansi.png)

### Getting the Original String
After parsing, you can retrieve the original text without markup tags:
```java
AnsiStringParser parser = Clique.parser();
String original = parser.getOriginalString("[red, bold]Hello[/] World"); // Returns "Hello World"
```

## Parser Rules

The parser follows a simple, predictable set of rules:

- `[red]` — valid, will be parsed and styled
- `[notastyle]` — valid syntax, but unrecognized style; passed through as-is
- `[nested[nested]]` — nested tags are not supported; passed through as-is
- `[not closed [red, blue] hello` — if a `[` is encountered before the current tag closes, the whole thing is ignored
- `\\[red]` — escaped bracket; rendered as literal `[red]`

The parser follows an **all or nothing** rule per tag — if a tag can't be cleanly parsed, it is left entirely untouched.

## Parser Configuration

Customize how the parser behaves using `ParserConfiguration`:
```java
ParserConfiguration configuration = ParserConfiguration
        .builder()
        .enableAutoCloseTags()  // Automatically reset styles between tags to prevent leaking
        .delimiter(' ')          // Use space instead of comma as delimiter
        .build();

AnsiStringParser configuredParser = Clique.parser(configuration);

// Now you can use space-separated styles
configuredParser.print("[red bold]Hello[blue] World");
```

### Configuration Options

- **`delimiter(char)`** - Set the delimiter between style attributes (default: `,`)
- **`enableAutoCloseTags()`** - Automatically resets styles when a new tag is encountered, preventing styles from leaking into subsequent tags
- **`enableStrictParsing()`** - Throw exceptions for unrecognized styles on otherwise valid tags

## Parser Exceptions
When strict parsing is enabled, the parser throws exceptions for unrecognized styles on valid tags:

### UnidentifiedStyleException

Thrown when a valid tag contains a style that doesn't exist:
```java
ParserConfiguration config = ParserConfiguration.builder()
    .enableStrictParsing()
    .build();
    
AnsiStringParser parser = Clique.parser(config);

// Throws UnidentifiedStyleException because "bol" is not a recognized style
parser.parse("[red, bol]Text[/]");
```

**Note:** Without strict parsing enabled, unrecognized styles are ignored and the text is passed through as-is. Malformed or structurally invalid tags are always passed through regardless of strict mode.

## Escaping Special Characters

Since `[]` brackets are used for markup tags, you can escape them with a backslash to display literal brackets.

### Escaping Brackets
```java
// Display literal [red]
Clique.parser().print("\\[red]");
```

**Examples:**
```java
"\\[red]"               // Displays: [red]
"\\[red, bold]"         // Displays: [red, bold]
"Coords: \\[10, 20]"    // Displays: Coords: [10, 20]
```

## Markup Syntax Reference

See [markup-reference.md](markup-reference.md) for a complete list of default supported colors, background colors, and text styles.

## Using Parser with Other Features

The parser is integrated into tables, boxes, and indenters. By default, markup parsing is enabled for all of these features:
```java
// Tables with markup
Clique.table(TableType.DEFAULT)
    .headers("[green, bold]Name[/]", "[green, bold]Age[/]")
    .row("[red]John[/]", "25")
    .render();

// Boxes with markup
Clique.box(BoxType.ROUNDED)
    .autosize()
    .content("[bold, blue]This is a configured box[/]")
    .render();

// Indenter with markup
Clique.indenter()
    .indent()
    .add("[blue, bold]Root[/]")
    .print();
```

You can also pass a custom configured parser to these features through their configuration objects.