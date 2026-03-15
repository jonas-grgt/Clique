# Frames

Frames are layout containers that vertically stack nested Clique components inside a bordered rectangle. Unlike boxes, frames have no opinion about text, they don't wrap, align, or manipulate string content. They just draw a border around whatever their children produce.

## Frame Types

Clique provides 4 built-in frame styles:

1. **DEFAULT** - Standard frame with simple borders
2. **CLASSIC** - Classic frame style
3. **ROUNDED** - Frame with rounded corners
4. **DOUBLE_LINE** - Frame with double-line borders

## Basic Usage

### Creating a Simple Frame

```java
Frame.builder()
    .nest("Hello from a frame")
    .build()
    .render();
```

### Nesting Components

Frames are designed to wrap existing Clique components:

```java
Table table = Clique.table(TableType.BOX_DRAW)
    .addHeaders("Name", "Age")
    .addRows("Alice", "25")
    .addRows("Bob", "30");

ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.BLOCKS);
bar.tick(70);

Clique.frame()
    .nest(table)
    .nest(bar)
    .build()
    .render();
```

### Adding a Title

```java
Clique.frame()
    .title("[bold]My App[/]")
    .nest(table)
    .build()
    .render();
```

## Frame Types

Specify a border style with `FrameType`:

```java
Clique.frame(FrameType.ROUNDED)
    .title("Stats")
    .nest(table)
    .build()
    .render();
```

## Nesting Content

### Nesting Components

Any Clique component that implements `Component` can be nested:

```java
Clique.frame()
    .nest(table)
    .nest(progressBar)
    .nest(indenter)
    .build()
    .render();
```

### Nesting Raw Strings

Raw strings and markup strings are both supported:

```java
Clique.frame()
    .nest("Plain string")
    .nest("[green, bold]Styled string[/]")
    .build()
    .render();
```

### Per-Node Alignment

Control how each child sits within the frame width using `FrameAlign`:

```java
Clique.frame()
    .nest(header, FrameAlign.CENTER)
    .nest(table, FrameAlign.LEFT)
    .nest("[dim]footer[/]", FrameAlign.RIGHT)
    .build()
    .render();
```

Available alignments:
- `FrameAlign.LEFT` - Left-aligned
- `FrameAlign.CENTER` - Centered
- `FrameAlign.RIGHT` - Right-aligned

## Width

By default, the frame derives its width from the widest nested component automatically. You can also set it explicitly:

```java
Clique.frame()
    .width(60)
    .nest(table)
    .build()
    .render();
```

If you don't call `.width()`, the frame measures all nested children and sizes itself to the widest one.

## Frame Configuration

Use `FrameConfiguration` to customize frame appearance and behavior.

### Basic Configuration

```java
FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .frameAlign(FrameAlign.LEFT)  // Default alignment for all children
    .padding(4)
    .build();

Clique.frame(FrameType.ROUNDED, config)
    .title("My Frame")
    .nest(table)
    .build()
    .render();
```

### Configuration Options

#### Default Alignment

Set the default alignment for all nested children. Individual `.nest()` calls can still override this per node:

```java
FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .frameAlign(FrameAlign.CENTER)
    .build();
```

#### Padding

Set the horizontal padding inside the frame:

```java
FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .padding(4)
    .build();
```

#### Border Styling

Style frame borders with different colors:

```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.CYAN)
    .verticalBorderStyles(ColorCode.MAGENTA)
    .edgeBorderStyles(ColorCode.YELLOW)
    .build();

FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .borderStyle(style)
    .build();

Clique.frame(FrameType.CLASSIC, config)
    .nest(table)
    .build()
    .render();
```

#### Custom Parser

Provide a custom configured parser for markup processing in string nodes and titles:

```java
ParserConfiguration parserConfig = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')
    .build();

FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .parser(Clique.parser(parserConfig))
    .build();
```

### Full Configuration Example

```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.CYAN)
    .verticalBorderStyles(ColorCode.MAGENTA)
    .edgeBorderStyles(ColorCode.YELLOW)
    .build();

FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .frameAlign(FrameAlign.CENTER)
    .padding(3)
    .borderStyle(style)
    .build();

Clique.frame(FrameType.DOUBLE_LINE, config)
    .title("[bold, cyan]Dashboard[/]", FrameAlign.CENTER)
    .nest(headerTable, FrameAlign.CENTER)
    .nest(progressBar, FrameAlign.LEFT)
    .nest("[dim]last updated: just now[/]", FrameAlign.RIGHT)
    .build()
    .render();
```

## Title Alignment

The frame title can be independently aligned within the top border:

```java
// Title on the left
Clique.frame()
    .title("My App", FrameAlign.LEFT)
    .nest(table)
    .build()
    .render();

// Title centered (default)
Clique.frame()
    .title("My App")
    .nest(table)
    .build()
    .render();

// Title on the right
Clique.frame()
    .title("My App", FrameAlign.RIGHT)
    .nest(table)
    .build()
    .render();
```

## Getting the Frame as a String

Use `get()` to retrieve the rendered frame as a string without printing:

```java
String frameString = Clique.frame()
    .title("Report")
    .nest(table)
    .build()
    .get();

System.out.println(frameString);
```

## Examples

### Dashboard Layout

```java
Table stats = Clique.table(TableType.ROUNDED_BOX_DRAW)
    .addHeaders("[cyan, bold]Metric[/]", "[cyan, bold]Value[/]")
    .addRows("Uptime", "[green]99.9%[/]")
    .addRows("Requests", "1,204,309")
    .addRows("Errors", "[red]42[/]");

ProgressBar cpu = Clique.progressBar(100, ProgressBarPreset.BLOCKS);
cpu.tick(73);

Clique.frame(FrameType.ROUNDED)
    .title("[bold, cyan]System Dashboard[/]")
    .nest(stats, FrameAlign.CENTER)
    .nest("[dim]CPU Usage[/]", FrameAlign.LEFT)
    .nest(cpu, FrameAlign.LEFT)
    .build()
    .render();
```

### Nested Indenter

```java
Indenter tree = Clique.indenter()
    .indent("├─")
    .add("[yellow]src/[/]")
    .indent()
    .add("Main.java")
    .add("Utils.java")
    .unindent()
    .indent("└─")
    .add("README.md");

Clique.frame(FrameType.CLASSIC)
    .title("Project Structure", FrameAlign.LEFT)
    .nest(tree)
    .build()
    .render();
```

### Styled Border Frame

```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.GREEN)
    .verticalBorderStyles(ColorCode.GREEN)
    .edgeBorderStyles(ColorCode.GREEN)
    .build();

FrameConfiguration config = FrameConfiguration.immutableBuilder()
    .borderStyle(style)
    .frameAlign(FrameAlign.CENTER)
    .build();

Clique.frame(FrameType.ROUNDED, config)
    .title("[green, bold]✓ Build Successful[/]")
    .nest("[green]All tests passed[/]", FrameAlign.CENTER)
    .nest(resultTable, FrameAlign.CENTER)
    .build()
    .render();
```

## Things to Watch Out For

- A nested component's content width **cannot exceed the frame's inner width**. If it does, an exception is thrown.
- The **title width cannot exceed the frame width**. Keep titles shorter than the frame's content.
- Frame width is derived from the **widest child** automatically, so you generally don't need to set it manually.

## See Also

- [Boxes Documentation](boxes.md) - Text containers with borders
- [Tables Documentation](tables.md) - Structured data display
- [Markup Reference](markup-reference.md) - Styling options for titles and string nodes
- [Parser Documentation](parser.md) - How markup parsing works