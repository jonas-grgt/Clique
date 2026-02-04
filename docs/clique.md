# Clique API Reference

The `Clique` class is the main entry point for the library. All components are created through static factory methods.

## Text Styling

### Parser
Parse markup tags to style text:
```java
Clique.parser().print("[red, bold]Error:[/] Something went wrong");
```

**Methods:**
- `parser()` - Create parser with default configuration
- `parser(ParserConfiguration)` - Create parser with custom configuration

### StyleBuilder
Fluent API for building styled strings:
```java
Clique.styleBuilder()
    .append("Success: ", ColorCode.GREEN, StyleCode.BOLD)
    .append("Operation completed", ColorCode.WHITE)
    .print();
```

**Methods:**
- `styleBuilder()` - Create a new style builder

## Tables

Create formatted tables with headers and rows.

### Basic Tables
```java
Clique.table()
    .addHeaders("Name", "Age", "Status")
    .addRows("Alice", "25", "Active")
    .render();
```

**Methods:**
- `table()` - Create table with default type and configuration
- `table(TableType)` - Create table with specific type
- `table(TableConfiguration)` - Create table with custom configuration
- `table(TableType, TableConfiguration)` - Full customization

**Available Types:**
`DEFAULT`, `COMPACT`/`MINIMAL`, `BOX_DRAW`, `ROUNDED_BOX_DRAW`, `MARKDOWN`

### Customizable Tables
Tables where you can customize borders:
```java
Clique.customizableTable()
    .addHeaders("Col1", "Col2")
    .customizeEdge('+')
    .customizeHorizontalLine('=')
    .customizeVerticalLine('|')
    .addRows("A", "B")
    .render();
```

**Methods:**
- `customizableTable()` - Default customizable table
- `customizableTable(TableType)` - Specify table type
- `customizableTable(TableConfiguration)` - Custom configuration
- `customizableTable(TableType, TableConfiguration)` - Full customization

## Boxes

Single-cell containers for displaying text with borders.

### Basic Boxes
```java
Clique.box()
    .withDimensions(40, 10)
    .content("Your message here")
    .render();
```

**Methods:**
- `box()` - Create box with default type and configuration
- `box(BoxType)` - Create box with specific type
- `box(BoxConfiguration)` - Create box with custom configuration
- `box(BoxType, BoxConfiguration)` - Full customization

**Available Types:**
`DEFAULT`, `CLASSIC`, `ROUNDED`, `DOUBLE_LINE`

### Customizable Boxes
Boxes where you can customize borders:
```java
Clique.customizableBox()
    .withDimensions(40, 10)
    .customizeEdge('<')
    .customizeVerticalLine('~')
    .customizeHorizontalLine('-')
    .content("Custom box")
    .render();
```

**Methods:**
- `customizableBox()` - Default customizable box
- `customizableBox(BoxType)` - Specify box type
- `customizableBox(BoxConfiguration)` - Custom configuration
- `customizableBox(BoxType, BoxConfiguration)` - Full customization

## Indenter

Create hierarchical text structures with nested indentation.
```java
Clique.indenter()
    .indent("→")
    .add("Root item")
    .indent("•")
    .add("Nested item")
    .unindent()
    .add("Back to root")
    .print();
```

**Methods:**
- `indenter()` - Create indenter with default configuration
- `indenter(IndenterConfiguration)` - Create with custom configuration

## Progress Bars

Visual feedback for long-running operations.
```java
ProgressBar bar = Clique.progressBar(100);
while (!bar.isDone()) {
    bar.tick();
    bar.render();
    Thread.sleep(50);
}
```

**Methods:**
- `progressBar(int total)` - Create with default style
- `progressBar(int total, ProgressBarConfiguration)` - Custom configuration
- `progressBar(int total, ProgressBarStyle)` - Use predefined style

**Predefined Styles:**
`BLOCKS`, `LINES`, `BOLD`, `CLASSIC`, `DOTS`

## Themes

Register and use color themes in your application.
```java
// Register a single theme
Clique.registerTheme("catppuccin-mocha");
Clique.parser().print("[ctp_mauve]Styled with Catppuccin![/]");

// Register all available themes
Clique.registerAllThemes();
```

**Methods:**
- `registerTheme(String name)` - Register a single theme by name
- `registerThemes(String... themes)` - Register multiple themes
- `registerThemes(Collection<String>)` - Register from collection
- `registerAllThemes()` - Register all available themes
- `discoverThemes()` - List all available themes
- `findTheme(String name)` - Find a specific theme

**Available Themes:**
`catppuccin-mocha`, `catppuccin-latte`, `dracula`, `gruvbox-dark`, `gruvbox-light`, `nord`, `tokyo-night`

## Custom Styles

Register custom color codes for use in markup.
```java
// Register single style
Clique.registerStyle("error", new RGBColor(255, 0, 0, false));
Clique.parser().print("[error]Custom error color[/]");

// Register multiple styles
Map<String, AnsiCode> styles = Map.of(
    "success", new RGBColor(0, 255, 0, false),
    "warning", new RGBColor(255, 255, 0, false)
);
Clique.registerStyles(styles);
```

**Methods:**
- `registerStyle(String name, AnsiCode code)` - Register single style
- `registerStyles(Map<String, AnsiCode>)` - Register multiple styles

## ANSI Color Control

Enable or disable ANSI color output.
```java
// Disable colors for CI/CD environments
Clique.enableCliqueColors(false);

// Re-enable colors
Clique.enableCliqueColors(true);
```

**Methods:**
- `enableCliqueColors(boolean enable)` - Control ANSI color output

## See Also
- [Markup Reference](markup-reference.md) - Available colors and styles
- [Themes Guide](themes.md) - Using and creating themes