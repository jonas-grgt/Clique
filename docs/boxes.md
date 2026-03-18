# Boxes

Boxes are single-cell containers that display text with borders and support text wrapping. They're perfect for displaying standalone messages, warnings, or long-form text.

## Box Types

Clique provides 4 built-in box styles:

1. **DEFAULT** - Standard box with ASCII borders
2. **CLASSIC** - Classic box style
3. **ROUNDED** - Box with rounded corners
4. **DOUBLE_LINE** - Box with double-line borders

![Boxes Output](../images/boxes.png)

## Basic Usage

### Creating a Simple Box
```java
Box box = Clique.box()
        .withDimensions(10, 20)  //Width, height
        .content("This is my first box");
    
box.render(); // Print the box to terminal
```

### Box Dimensions

- **Width** - The horizontal size of the box (in characters)
- **Height** - The vertical size of the box (in lines)
```java
Box box = Clique.box(BoxType.DEFAULT)
        .withDimensions(50, 5) //Width, Height
        .content("A wider, shorter box")
        .render();
```

### Using Markup in Boxes

Boxes automatically parse markup tags in content:
```java
Box box = Clique.box()
    .withDimensions(40, 5)
    .content("[yellow, bold]Warning:[/] This is an important message that needs attention")
    .render();
```

### Multi-line Content

Boxes handle newlines properly and will wrap text accordingly:
```java
Box box = Clique.box(BoxType.CLASSIC)
    .withDimensions(40, 10)
    .content(
        "[green, bold]Success![/]\n\n" +
        "Your operation completed successfully.\n" +
        "You can now proceed to the next step."
    )
    .render();
```

## Box Configuration

Use `BoxConfiguration` to customize box appearance and behavior.

### Basic Configuration
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
        .textAlign(TextAlign.CENTER)
        .autoSize()
        .build();

Box box = Clique.box(BoxType.DOUBLE_LINE, config)
        .noDimensions()
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

Reduces the drawable area of the box from both sides, creating inner breathing room around the content:
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .textAlign(TextAlign.CENTER)
    .centerPadding(3)  // Shrinks drawable area by 3 characters on each side
    .build();
```

> **Note:** `centerPadding` affects both horizontal and vertical space. A value of `2` means the content area is reduced by 2 characters on all sides.
#### Auto Size

Let the box automatically resize to fit its content:
```java
BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .autoSize()
    .build();

Box box = Clique.box( config)
    .noDimensions()   
    .content("This box will size itself")
    .render();
```

When `autoSize` is enabled, the box will automatically adjust dimensions even if the content can't wrap properly.

#### Border Styling

Style box borders with different colors:
```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalStyle(ColorCode.CYAN)
    .verticalStyle(ColorCode.MAGENTA)
    .cornerStyle(ColorCode.YELLOW)
    .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .borderStyle(style)
    .build();

Clique.box(BoxType.CLASSIC, config)
        .withDimensions(20, 10)
        .content("Styled Box")
        .render();
```

#### Custom Parser

Provide a custom configured parser for markup processing:
```java
ParserConfiguration parserConfig = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')
    .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .parser(Clique.parser(parserConfig))
    .build();
```

### Full Configuration Example
```java
BorderStyle style = BorderStyle.immutableBuilder()
        .uniformStyle(blue)
        .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .borderStyle(style)
    .textAlign(TextAlign.CENTER)
    .autoSize()
    .parser(Clique.parser())
    .build();

Box box = Clique.box(BoxType.DOUBLE_LINE, config)
    .noDimensions()    
    .content("[bold, blue]This is a configured box[/]")
    .render();
```

## Customizing Boxes

All box types support border customization, which returns a `Box` for fluent chaining:
```java
BorderStyle style = BorderStyle.immutableBuilder()
        .uniformStyle(blue)
        .cornerChar('*')
        .horizontalChar('~')
        .verticalChar('I')
        .build();

BoxConfiguration config = BoxConfiguration.immutableBuilder()
    .autoSize()
    .build();

Clique.box(config)
    .noDimensions()
    .content("[red]This is my custom box :)[/]")
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
    .noDimensions()
    .content("[red, bold]⚠ ALERT ⚠[/]\n\nSystem maintenance in progress")
    .render();
```

### Info Box
```java
Clique.box(BoxType.ROUNDED)
    .withDimensions(60, 10)
    .content(
        "[blue, bold]ℹ Information[/]\n\n" +
        "This feature is currently in beta. " +
        "Please report any issues you encounter."
    )
    .render();
```

## Things to Watch Out For

- When using `autoSize`, you don't need to specify width or length, you can just use `noDimensions()`
- Using `noDimensions` without an `autosize` config parameter throws an `IllegalStateEx`
- Blank chars for customization are not applied, and the previous default char of the `BoxType` is used instead

## See Also

- [Markup Reference](markup-reference.md) - Styling options for box content
- [Parser Documentation](parser.md) - How markup parsing works