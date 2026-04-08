# StyleBuilder

StyleBuilder provides a fluent API for chaining styled strings together. It's perfect when you prefer a programmatic and type safe pattern over markup syntax.

## Basic Usage

### Append

The `append()` method applies styles to text and automatically resets the terminal style after each call:
```java
Clique.styleBuilder()
    .append("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
    .append("World", ColorCode.RED, StyleCode.DIM, StyleCode.REVERSE_VIDEO)
    .print();
```

Each `append()` call starts with a clean slate, so styles don't carry over to the next text.

### Stack

The `stack()` method applies styles but doesn't reset the terminal style. This gives you more control:
```java
Clique.styleBuilder()
    .stack("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
    .stack("World", ColorCode.RED, StyleCode.DIM, StyleCode.REVERSE_VIDEO)
    .print();
```

Use `stack()` when you want styles to accumulate or when you need manual control over when styles reset.


## Getting the Result

### Get Styled String

Retrieve the built string without printing it:
```java
String styledText = Clique.styleBuilder()
    .stack("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
    .stack("World", ColorCode.RED, StyleCode.DIM);

// Use it later
System.out.println(styledText);
```

## Available Codes

### ColorCode Options

All standard and bright ANSI colors are available:
- Standard: `RED`, `GREEN`, `YELLOW`, `BLUE`, `MAGENTA`, `CYAN`, `WHITE`, `BLACK`
- Bright: `BRIGHT_RED`, `BRIGHT_GREEN`, `BRIGHT_YELLOW`, etc.

### StyleCode Options

All ANSI text styles are supported:
- `BOLD` - Bold/emphasized text
- `DIM` - Dimmed text
- `ITALIC` - Italic text
- `UNDERLINE` - Underlined text
- `REVERSE_VIDEO` - Swap foreground/background
- `INVISIBLE` - Hidden text
- `STRIKE` - Strikethrough text
- `DOUBLE_UNDERLINE` - Double underlined text

### Background Colors

Background colors follow the pattern `BG_[COLOR]`:
- Standard: `BG_RED`, `BG_GREEN`, `BG_YELLOW`, etc.
- Bright: `BG_BRIGHT_RED`, `BG_BRIGHT_GREEN`, etc.

## Examples

### Success/Error Messages
```java
// Success message
Clique.styleBuilder()
    .append("✓ ", ColorCode.GREEN, StyleCode.BOLD)
    .append("Build successful", ColorCode.WHITE)
    .print();

// Error message
Clique.styleBuilder()
    .append("✗ ", ColorCode.RED, StyleCode.BOLD)
    .append("Build failed: ", ColorCode.RED)
    .append("Missing dependency", ColorCode.WHITE, StyleCode.DIM)
    .print();
```

### Highlighted Output
```java
Clique.styleBuilder()
    .append("Warning: ", ColorCode.YELLOW, StyleCode.BOLD)
    .append("This action cannot be undone", ColorCode.WHITE)
    .print();
```

### Multi-line Styled Text
```java
String output = Clique.styleBuilder()
    .append("Title: ", ColorCode.CYAN, StyleCode.BOLD)
    .append("My Document\n", ColorCode.WHITE)
    .append("Status: ", ColorCode.CYAN, StyleCode.BOLD)
    .append("Complete", ColorCode.GREEN)
    .get();

System.out.println(output);
```

## See Also

- [Parser Documentation](parser.md) - Markup-based alternative to StyleBuilder
- [Markup Reference](markup-reference.md) - All available colors and styles