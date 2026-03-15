# Tips and Tricks

This guide covers useful tips, workarounds, and best practices when using Clique.

## Escaping Special Characters

### Escaping Brackets

Since `[]` brackets are used for styling tags in markup, you need to escape them when displaying literal brackets.

To display literal brackets, use `[/]` to close the tag interpretation:
```java
// Display literal [123, 456]
Clique.parser().print("[123, 456[/]]");
```

**Pattern:** `[your text with brackets[/]]`

**Examples:**
```java
"[red, bold[/]]"        // Displays: [red, bold]
"[x, y, z[/]]"          // Displays: [x, y, z]
"Coords: [10, 20[/]]"   // Displays: Coords: [10, 20]
```

**In Tables:**
```java
Clique.table(TableType.DEFAULT)
    .addHeaders("[123, 456[/]]", "text asd", "another qwe")
    .render();
```

## StyleBuilder Tips

### Getting Styled Strings

Use the `get()` method to build styled text without printing it immediately:
```java
// Build styled text without printing
StyleBuilder sb = Clique.styleBuilder();
String styledText = sb
    .append("Success: ", ColorCode.GREEN, StyleCode.BOLD)
    .append("Operation completed", ColorCode.WHITE)
    .get();

// Use the styled text later
System.out.println(styledText);
```

This is useful when you need to:
- Store styled strings for later use
- Pass styled strings to other methods
- Build strings conditionally before output

### Reusing StyleBuilder

You can reuse a `StyleBuilder` instance by calling methods multiple times:
```java
StyleBuilder easingConfigurationBuilder = Clique.styleBuilder();

// First message
easingConfigurationBuilder.append("Loading... ", ColorCode.YELLOW).print();

// Do some work...

// Second message (reuses same easingConfigurationBuilder)
easingConfigurationBuilder.append("Done!", ColorCode.GREEN).print();
```

## Some Table Tips

### Customizable Table Shorthand

Customizable tables allow you to modify edges, vertical lines, and horizontal lines. Currently, only the `DEFAULT` table type supports this.
```java
// With configuration
TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .padding(2)
    .build();

Clique.customizableTable(TableType.DEFAULT, config)
    .addHeaders("Col1", "Col2")
    .customizeEdge('*')
    .customizeHorizontalLine('=')
    .customizeVerticalLine('|')
    .addRows("A", "B")
    .render();

// Without configuration (uses defaults)
Clique.customizableTable(TableType.DEFAULT)
    .addHeaders("Name", "Age")
    .customizeEdge('+')
    .customizeHorizontalLine('-')
    .customizeVerticalLine('|')
    .addRows("Alice", "25")
    .render();
```

### Dynamic Table Updates

Build tables incrementally or modify them after creation:
```java
Table table = Clique.table(TableType.BOX_DRAW)
    .addHeaders("Name", "Score");

// Add rows dynamically
for (Player player : players) {
    table.addRows(player.getName(), String.valueOf(player.getScore()));
}

// Update based on conditions
if (needsUpdate) {
    table.updateCell(1, 1, "Updated Value");
}

table.render();
```

### Getting Table Strings

Use `get()` to get the table as a string without printing:
```java
Table table = Clique.table(TableType.MARKDOWN)
    .addHeaders("Item", "Price")
    .addRows("Widget", "$10");

String tableString = table.get();

// Now you can log it, save it, or process it
logger.info(tableString);
```

## Some Box Tips

### Auto-Sizing Boxes

When you enable `autoSize`, you don't need to specify width or length:
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .autoSize()
    .build();

// No width/length needed
Clique.box(BoxType.ROUNDED, config)
    .noDimensions()
    .content("This box sizes itself automatically")
    .render();
```

### Multi-line Content

Boxes handle newlines properly, so you can use `\n` for line breaks, though they don't handle preformatted content well
```java
Clique.box(BoxType.CLASSIC)
    .width(50)
    .content(
        "[bold]Title[/]\n" +
        "\n" +
        "First paragraph of text.\n" +
        "\n" +
        "Second paragraph of text."
    )
    .render();
```

## Parser Tips

### Custom Delimiters

If commas don't work for your use case, configure a custom delimiter:
```java
ParserConfiguration config = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')  // Use spaces instead of commas
    .build();

AnsiStringParser parser = Clique.parser(config);
parser.print("[red bold]Hello[/] [blue italic]World[/]");
```

### Strict vs Lenient Parsing

Enable strict parsing to catch errors during development:
```java
// Strict mode will throw exceptions for invalid styles
ParserConfiguration strict = ParserConfiguration
    .immutableBuilder()
    .enableStrictParsing()
    .build();

// Lenient mode (default) ignores invalid styles and print them as is
ParserConfiguration lenient = ParserConfiguration.DEFAULT;
```

Use strict mode during development to catch typos, and lenient mode in production to handle gracefully.

### Auto-Close Tags

Enable auto-close to automatically close unclosed tags:
```java
ParserConfiguration config = ParserConfiguration
    .immutableBuilder()
    .enableAutoCloseTags()
    .build();

AnsiStringParser parser = Clique.parser(config);

// No need to manually close with [/]
parser.print("[red, bold]This tag auto-closes");
```

### Force Enable Colors
```java
Clique.enableCliqueColors(true);  // Force enable colors
```

Use this when:
- Running in environments where ANSI detection fails
- Testing color output
- Working with terminals that support ANSI but aren't detected

### Force Disable Colors
```java
Clique.enableCliqueColors(false);  // Force disable colors
```

Use this when:
- Generating logs for systems that don't support ANSI
- Creating plain text output
- Working in environments where colors cause issues

### Checking Support
```java
// Colors will be applied if terminal supports it
Clique.parser().print("[red]This might be colored[/]");

// After forcing
Clique.enableCliqueColors(true);
Clique.parser().print("[red]This will definitely be colored[/]");
```

## Common Gotchas

### Column Alignment Precedence

When using tables, remember that column alignment always overrides table-wide alignment:
```java
TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .alignment(CellAlign.CENTER)           // All cells centered
    .columnAlignment(0, CellAlign.LEFT)    // Column 0 will be left (overrides)
    .build();
```

### Parser and Configuration Reuse

You can configure a parser once and reuse it throughout your application:
```java
// Configure once
ParserConfiguration config = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')
    .enableAutoCloseTags()
    .build();

AnsiStringParser parser = Clique.parser(config);

// Reuse throughout your app
parser.print("[red bold]Error message[/]");
parser.print("[green]Success message[/]");
```

## Performance Tips

### Batch Operations

When building large tables or indenters, batch your operations:
```java
TableHeaderBuilder table = Clique.table(TableType.DEFAULT);
table.addHeaders("Name", "Value");

for (Item item : items) {
    table.addRows(item.getName(), item.getValue());
}
table.render();
```

### Reuse Configurations

Create configuration objects once and reuse them:
```java
// Create once
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .autoSize()
    .textAlign(TextAlign.CENTER)
    .build();

// Reuse multiple times
Clique.box(BoxType.ROUNDED, config).noDimensions().content("Box 1").render();
Clique.box(BoxType.ROUNDED, config).noDimensions().content("Box 2").render();
Clique.box(BoxType.ROUNDED, config).noDimensions().content("Box 3").render();
```

## See Also

- [Parser Documentation](parser.md) - Parser configuration details
- [Markup Reference](markup-reference.md) - All available styles
- [Tables Documentation](tables.md) - Table-specific tips
- [Boxes Documentation](boxes.md) - Box-specific tips