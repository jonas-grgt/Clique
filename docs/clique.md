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

### RGB Colors
Create custom RGB colors for use with the style API:
```java
RGBAnsiCode color = Clique.rgb(255, 128, 0);
RGBAnsiCode bgColor = Clique.rgb(255, 128, 0, true); // background color
```

**Methods:**
- `rgb(int r, int g, int b)` - Create an RGB foreground color
- `rgb(int r, int g, int b, boolean background)` - Create an RGB color, optionally as background

## Tables

Create formatted tables with headers and rows.

```java
Clique.table()
    .headers("Name", "Age", "Status")
    .row("Alice", "25", "Active")
    .render();
```

**Methods:**
- `table()` - Create table with default type and configuration
- `table(TableType)` - Create table with specific type
- `table(TableConfiguration)` - Create table with custom configuration
- `table(TableType, TableConfiguration)` - Full customization
- `table(BorderSpec)` - Create table with uniform border styling
- `table(TableType, BorderSpec)` - Create table with specific type and uniform border styling

**Available Types:**
`DEFAULT`, `COMPACT`/`MINIMAL`, `BOX_DRAW`, `ROUNDED_BOX_DRAW`, `MARKDOWN`

> **Note:** `customizableTable()` and its overloads are deprecated as of 3.1. Use the `table()` methods above instead. They will be removed in a future release.

## Boxes

Single-cell containers for displaying text with borders.

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
- `box(BorderSpec)` - Create box with uniform border styling
- `box(BoxType, BorderSpec)` - Create box with specific type and uniform border styling

**Available Types:**
`DEFAULT`, `CLASSIC`, `ROUNDED`, `DOUBLE_LINE`

> **Note:** `customizableBox()` and its overloads are deprecated as of 3.1. Use the `box()` methods above instead. They will be removed in a future release.

## Frames

A frame is a box-based container for displaying multi-line or structured content.

```java
Clique.frame()
    .nest("Line one")
    .nest("Line two")
    .render();
```

**Methods:**
- `frame()` - Create frame with default type and configuration
- `frame(BoxType)` - Create frame with specific box type
- `frame(FrameConfiguration)` - Create frame with custom configuration
- `frame(BoxType, FrameConfiguration)` - Full customization
- `frame(BorderSpec)` - Create frame with uniform border styling
- `frame(BoxType, BorderSpec)` - Create frame with specific type and uniform border styling

## Trees

Render hierarchical data as an indented tree structure.

```java
Clique.tree("root")
    .add("child one")
    .add("child two")
    .print();
```

**Methods:**
- `tree(String label)` - Create a tree with the given root label
- `tree(String label, TreeConfiguration)` - Create a tree with custom configuration

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
- `progressBar(int total, ProgressBarPreset)` - Use predefined preset
- `progressBar(int total, EasingConfiguration)` - Create with easing animation

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
//Force disable colors 
Clique.enableCliqueColors(false);

// Re enable colors
Clique.enableCliqueColors(true);

// Enable colors (no-arg shorthand)
Clique.enableCliqueColors();
```

**Methods:**
- `enableCliqueColors(boolean enable)` - Control ANSI color output
- `enableCliqueColors()` - Enable ANSI color output (shorthand)

## See Also
- [Markup Reference](markup-reference.md) - Available colors and styles
- [Themes Guide](themes.md) - Using and creating themes