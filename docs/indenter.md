# Indenter

Indenter lets you build styled, nested text structures with full control over indentation symbols and spacing. Think bullet lists, CLI menus, task trackers, or any output where you want different symbols at different levels — not auto-generated tree connectors.

> **Heads up:** If you're trying to render a file tree or parent-child hierarchy with `├─` / `└─` connectors, use [`Tree`](tree.md) instead. Indenter can technically fake those connectors, but you'll be fighting it the whole way. Indenter shines when the *structure itself* is your design choice, not derived from data.

## Basic Usage

```java
Indenter indenter = Clique.indenter()
    .indent()
    .add("Root Level")
    .indent()
    .add("Nested Item 1")
    .add("Nested Item 2")
    .unindent()
    .add("Back to Root");

indenter.print();
```

### Custom Flags Per Level

Flags are the characters/symbols that appear at the start of each indented line. You can set a different one per level:

```java
Clique.indenter()
    .indent(2, "-")
    .add("First item")
    .add("Second item")
    .indent(2, "•")
    .add("Nested item")
    .unindent()
    .add("Back to first level")
    .print();
```

## Indent Methods

```java
indenter.indent(4, "→");   // specific level + flag
indenter.indent(3);         // specific level, uses default flag
indenter.indent("•");       // default level from config, custom flag
indenter.indent();          // both from config defaults
```

## Managing Levels

```java
indenter.unindent();     // pop back one level
indenter.resetLevel();   // reset to 0, keeps current flag
```

## Adding Content

```java
indenter.add("Single item");
indenter.add("Item A", "Item B", "Item C");   // varargs

List<String> items = List.of("X", "Y", "Z");
indenter.add(items);                           // collection

indenter.add(42);                              // any object, calls toString()
```

## Built-in Flags

The `Flag` enum covers the most common symbols:

```java
Clique.indenter()
    .indent(Flag.BULLET)          // •
    .add("Bullet item")
    .indent(Flag.ARROW)           // →
    .add("Arrow item")
    .indent(Flag.HYPHEN)          // -
    .add("Hyphen item")
    .indent(Flag.ASTERISK)        // *
    .add("Asterisk item")
    .indent(Flag.SQUARE_BULLET)   // ▪
    .add("Square bullet item")
    .indent(Flag.CIRCLE)          // ○
    .add("Circle item")
    .indent(Flag.RIGHT_TRIANGLE)  // ▸
    .add("Triangle item");
```

Available flags:
- `Flag.BULLET` - `•`
- `Flag.ARROW` - `→`
- `Flag.HYPHEN` - `-`
- `Flag.ASTERISK` - `*`
- `Flag.SQUARE_BULLET` - `▪`
- `Flag.CIRCLE` - `○`
- `Flag.RIGHT_TRIANGLE` - `▸`

## Configuration

```java
IndenterConfiguration config = IndenterConfiguration.immutableBuilder()
    .indentLevel(4)            // spaces per indent level (default: 2)
    .defaultFlag("→")          // flag used when none is specified
    .parser(Clique.parser())   // markup parsing is on by default
    .build();

Clique.indenter(config)
    .indent()
    .add("[blue, bold]Root[/]")
    .indent()
    .add("[green]Nested item[/]")
    .print();
```

### Custom Parser

```java
ParserConfiguration parserConfig = ParserConfiguration.immutableBuilder()
    .delimiter(' ')
    .build();

IndenterConfiguration config = IndenterConfiguration.immutableBuilder()
    .parser(Clique.parser().configuration(parserConfig))
    .build();
```

## Getting and Clearing Content

```java
String result = indenter.get();   // get rendered string without printing
indenter.flush();                  // clear content + reset indent levels
```

## Markup Support

Markup tags work anywhere — in flags, content, or both:

```java
Clique.indenter()
    .indent("[cyan]▸[/] ")
    .add("[bold]Section Title[/]")
    .indent()
    .add("[green]Item one[/]")
    .add("[yellow]Item two[/]")
    .print();
```

## Examples

### Task / Todo List

Indenter is a natural fit for checklists where done/pending items need distinct symbols:

```java
Clique.indenter()
    .indent("☐ ")
    .add("[bold]Project Tasks[/]")
    .indent("  ☐ ")
    .add("Design phase")
    .add("Development")
    .indent("    ☑ ")
    .add("[dim, strike]Setup environment[/]")
    .add("[dim, strike]Write tests[/]")
    .unindent()
    .add("Code review")
    .unindent()
    .add("Deployment")
    .print();
```

### CLI Menu

Nested menus where each level has a consistent but distinct visual treatment:

```java
IndenterConfiguration config = IndenterConfiguration.immutableBuilder()
    .indentLevel(3)
    .defaultFlag("▸ ")
    .build();

Clique.indenter(config)
    .indent()
    .add("[cyan, bold]Main Menu[/]")
    .indent()
    .add("File")
    .indent()
    .add("New")
    .add("Open")
    .add("Save")
    .unindent()
    .add("Edit")
    .indent()
    .add("Cut")
    .add("Copy")
    .add("Paste")
    .unindent()
    .add("View")
    .print();
```

### Styled Log / Report Output

When you want structured, scannable CLI output with visual hierarchy but no tree connectors:

```java
Clique.indenter()
    .indent("[bold]>[/] ")
    .add("[bold, cyan]Build Report[/]")
    .indent("  [green]✓[/] ")
    .add("Compiled 42 files")
    .add("Tests passed: 108/108")
    .unindent()
    .indent("  [yellow]⚠[/] ")
    .add("2 deprecation warnings")
    .unindent()
    .indent("  [red]✗[/] ")
    .add("Coverage below threshold (74%)")
    .print();
```

## See Also

- [Tree Documentation](tree.md) - Better choice for hierarchical data with auto-generated connectors
- [Markup Reference](markup-reference.md) - Styling options for indented content
- [Parser Documentation](parser.md) - How markup parsing works