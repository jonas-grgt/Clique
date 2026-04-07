# Boxes

Boxes are single-cell containers that display text with borders. They're perfect for displaying standalone messages, warnings, or long-form text.

## Box Types

Clique provides 4 built-in box styles:

1. **DEFAULT** - Standard box with ASCII borders
2. **CLASSIC** - Classic box style
3. **ROUNDED** - Box with rounded corners (default)
4. **DOUBLE_LINE** - Box with double-line borders

![Boxes Output](../images/boxes.png)

## Basic Usage

### Creating a Simple Box
```java
Box box = Clique.box()
    .dimensions(10, 20)  // Width, height
    .content("This is my first box");

box.render(); // Print the box to terminal
```

### Box Dimensions

- **Width** - The horizontal size of the box (in characters)
- **Height** - The vertical size of the box (in lines)
```java
Clique.box()
    .dimensions(50, 5)  // Width, height
    .content("A wider, shorter box")
    .render();
```

**AutoSized boxes**
No dimension set, autosizes the box
```java
Clique.box()
    .content("An autosized box")
    .render();
```

### Using Markup in Boxes

Boxes automatically parse markup tags in content:
```java
Clique.box()
    .dimensions(40, 5)
    .content("[yellow, bold]Warning:[/] This is an important message that needs attention")
    .render();
```

### Multi-line Content

Boxes handle newlines properly and will wrap text accordingly:
```java
Clique.box(BoxType.CLASSIC)
    .dimensions(40, 10)
    .content(
        """
            [green, bold]Success![/]
            Your operation completed successfully.
            You can now proceed to the next step.
        """
    )
    .render();
```

### Text Alignment

Boxes support a range of text alignments, with the default being centered:
```java
Clique.box(BoxType.CLASSIC)
    .autosize()
    .content(
        """
            [green, bold]Success![/]
            Your operation completed successfully.
            You can now proceed to the next step.
        """, TextAlign.CENTER
    )
    .render();
```

Available alignments:
- `TextAlign.TOP_LEFT`, `TextAlign.TOP_CENTER`, `TextAlign.TOP_RIGHT`
- `TextAlign.CENTER_LEFT`, `TextAlign.CENTER`, `TextAlign.CENTER_RIGHT`
- `TextAlign.BOTTOM_LEFT`, `TextAlign.BOTTOM_CENTER`, `TextAlign.BOTTOM_RIGHT`

Default `TextAlign` is `CENTER`
## Box Configuration

Use `BoxConfiguration` to customize box appearance and behavior.

### Basic Configuration
```java
Clique.box(BoxType.DOUBLE_LINE)
    .content("This box auto-sizes to fit content")
    .render();
```

### Configuration Options

#### Text Alignment

Control how content is aligned within the box:
```java
BoxConfiguration config = BoxConfiguration.builder()
    .textAlign(TextAlign.CENTER)
    .build();
```

#### Padding

Adds padding to each side of the box, this padding is taken from the given width of the box, and is not added to the box
```java
BoxConfigurationBuilder builder = BoxConfiguration.builder().padding(3).build();
```
#### Border Coloring

For quick uniform border coloring
```java
// Static factory
Clique.box(BorderColor.of(ColorCode.BLUE))
    .dimensions(40, 10)
    .content("Blue border box")
    .render();

// With a specific box type
Clique.box(BoxType.CLASSIC, "blue")
    .dimensions(40, 10)
    .content("Blue border box")
    .render();
```

`BorderStyle` also works directly as a `BorderSpec` for backward compatibility:
```java
Clique.box(BorderStyle.builder().uniformStyle("blue").build())
    .dimensions(40, 10)
    .content("Blue border box")
    .render();
```

#### Custom Parser

Provide a custom configured parser for markup processing:
```java
ParserConfiguration parserConfig = ParserConfiguration
    .builder()
    .delimiter(' ')
    .build();

BoxConfiguration config = BoxConfiguration.builder()
    .parser(Clique.parser(parserConfig))
    .build();
```

### Full Configuration Example
```java
BoxConfigurationBuilder builder = BoxConfiguration.builder()
    .borderStyle(BorderColor.of("blue"))
    .textAlign(TextAlign.CENTER)
    .parser(Clique.parser());
BoxConfiguration config = builder.build();

Clique.box(BoxType.DOUBLE_LINE, config)
    .content("[bold, blue]This is a configured box[/]")
    .render();
```

## Border Char Customization

All box types support custom border characters via `BorderStyle`:
```java
BorderStyle style = BorderStyle.builder()
    .uniformStyle("blue")
    .cornerChar('*')
    .horizontalChar('~')
    .verticalChar('I')
    .build();

Clique.box(style)
    .dimensions(40, 10)
    .content("[red]This is my custom box :)[/]")
    .render();
```

## Examples

### Alert Box
```java
Clique.box(BoxType.DOUBLE_LINE, config)
    .content("[red, bold]⚠ ALERT ⚠[/]\n\nSystem maintenance in progress")
    .render();
```

### Info Box
```java
Clique.box()
    .dimensions(60, 10)
    .content(
        "[blue, bold]ℹ Information[/]\n\n" +
        "This feature is currently in beta. " +
        "Please report any issues you encounter."
    )
    .render();
```

## Things to Watch Out For
- `Clique.box()` defaults to `BoxType.ROUNDED`
- Blank chars for customization are not applied; the previous default char of the `BoxType` is used instead

## See Also

- [Markup Reference](markup-reference.md) - Styling options for box content
- [Parser Documentation](parser.md) - How markup parsing works