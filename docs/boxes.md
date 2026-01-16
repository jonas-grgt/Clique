# Boxes

Boxes are single-cell containers that display text with borders and support text wrapping. They're perfect for displaying standalone messages, warnings, or long-form text.

## Box Types

Clique provides 4 built-in box styles:

1. **DEFAULT** - Standard box with simple borders
2. **CLASSIC** - Classic box style
3. **ROUNDED** - Box with rounded corners
4. **DOUBLE_LINE** - Box with double-line borders

![Boxes Output](../images/boxes.png)

## Basic Usage

### Creating a Simple Box
```java
Box box = Clique.box(BoxType.CLASSIC)
    .width(40)
    .length(10)
    .content("This is my first box");
    
box.render(); // Print the box to terminal
```

### Box Dimensions

- **Width** - The horizontal size of the box (in characters)
- **Length** - The vertical size of the box (in lines)
```java
Box box = Clique.box(BoxType.ROUNDED)
    .width(50)
    .length(5)
    .content("A wider, shorter box")
    .render();
```

## Box Configuration

Use `BoxConfiguration` to customize box appearance and behavior.

**Note:** Markup parsing is enabled by default.

### Basic Configuration
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .textAlign(TextAlign.CENTER)
    .autoSize()
    .build();

Box box = Clique.box(BoxType.DOUBLE_LINE, config)
    .content("This box auto-sizes to fit content")
    .render();
```

### Configuration Options

#### Text Alignment

Control how content is aligned within the box:
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .textAlign(TextAlign.CENTER)
    .build();
```

Available alignments:
- `TextAlign.LEFT` - Left-aligned (default)
- `TextAlign.CENTER` - Centered
- `TextAlign.RIGHT` - Right-aligned

#### Center Padding

Add padding from both sides when content is centered:
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .textAlign(TextAlign.CENTER)
    .centerPadding(3)  // 3 spaces on each side
    .build();
```

#### Auto Size

Let the box automatically resize to fit its content:
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .autoSize()  // No need to specify width/length
    .build();

Box box = Clique.box(BoxType.ROUNDED, config)
    .content("This box will size itself")
    .render();
```

When `autoSize` is enabled, the box will automatically adjust dimensions even if the content can't wrap properly.

#### Border Styling

Style box borders with different colors:
```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.CYAN)
    .verticalBorderStyles(ColorCode.MAGENTA)
    .edgeBorderStyles(ColorCode.YELLOW)
    .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .borderStyle(style)
    .build();

Box box = Clique.box(BoxType.CLASSIC, config);
```

#### Custom Parser

Provide a custom configured parser for markup processing:
```java
ParserConfiguration parserConfig = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')
    .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .parser(Clique.parser().configuration(parserConfig))
    .build();
```

### Full Configuration Example
```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.CYAN)
    .verticalBorderStyles(ColorCode.MAGENTA)
    .edgeBorderStyles(ColorCode.YELLOW)
    .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .borderStyle(style)
    .textAlign(TextAlign.CENTER)
    .centerPadding(3)
    .autoSize()
    .parser(Clique.parser())
    .build();

Box box = Clique.box(BoxType.DOUBLE_LINE, config)
    .content("[bold, blue]This is a configured box[/]")
    .render();
```

## Customizable Boxes

Customizable boxes let you modify edges, vertical lines, and horizontal lines. Currently, only the `DEFAULT` box type supports customization.

### Basic Customization
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .autoSize()
    .build();

Box box = Clique.customizableBox(BoxType.DEFAULT, config)
    .customizeEdge('<')
    .customizeVerticalLine('~')
    .customizeHorizontalLine('-')
    .content("[red]This is my custom box :)[/]")
    .render();
```

### Customization Methods

- `customizeEdge(char)` - Change corner characters
- `customizeVerticalLine(char)` - Change vertical border character
- `customizeHorizontalLine(char)` - Change horizontal border character

## Using Markup in Boxes

Boxes automatically parse markup tags in content:
```java
Box box = Clique.box(BoxType.ROUNDED)
    .width(50)
    .content("[yellow, bold]Warning:[/] This is an important message that needs attention")
    .render();
```

### Multi-line Content

Boxes handle newlines properly and will wrap text accordingly:
```java
Box box = Clique.box(BoxType.CLASSIC)
    .width(40)
    .content(
        "[green, bold]Success![/]\n\n" +
        "Your operation completed successfully.\n" +
        "You can now proceed to the next step."
    )
    .render();
```

## Examples

### Alert Box
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .textAlign(TextAlign.CENTER)
    .autoSize()
    .build();

Clique.box(BoxType.DOUBLE_LINE, config)
    .content("[red, bold]⚠ ALERT ⚠[/]\n\nSystem maintenance in progress")
    .render();
```

### Info Box
```java
Clique.box(BoxType.ROUNDED)
    .width(60)
    .content(
        "[blue, bold]ℹ Information[/]\n\n" +
        "This feature is currently in beta. " +
        "Please report any issues you encounter."
    )
    .render();
```

### Custom Styled Box
```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.GREEN)
    .verticalBorderStyles(ColorCode.GREEN)
    .edgeBorderStyles(ColorCode.GREEN)
    .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .borderStyle(style)
    .textAlign(TextAlign.CENTER)
    .centerPadding(2)
    .build();

Clique.box(BoxType.ROUNDED, config)
    .width(50)
    .content("[green, bold]✓ Build Successful[/]\n\nAll tests passed")
    .render();
```

## Things to Watch Out For

- **Emojis** will mess with width calculations. Try to avoid using them in boxes.
- When using `autoSize`, you don't need to specify width or length.

## See Also

- [Markup Reference](markup-reference.md) - Styling options for box content
- [Parser Documentation](parser.md) - How markup parsing works