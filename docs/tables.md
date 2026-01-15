# Tables

Tables help you display structured data in a clean, formatted way. Clique supports multiple table styles with extensive customization options.

## Table Types

Clique provides 5 built-in table styles:

1. **DEFAULT** - Standard table with lines
2. **COMPACT** (or **MINIMAL**) - Minimalist table with fewer borders
3. **BOX_DRAW** - Table using box-drawing characters
4. **ROUNDED_BOX_DRAW** - Box-draw table with rounded corners
5. **MARKDOWN** - Markdown-style table format

![Clique Tables](images/tables.png)

## Basic Usage

### Creating a Simple Table
```java
Table table = Clique.table(TableType.DEFAULT);
table.addHeaders("Name", "Age", "Class")
    .addRows("John", "25", "Class A")
    .addRows("Doe", "26", "Class B");

table.render(); // Print the table to terminal
```

### Compact Table
```java
Table table = Clique.table(TableType.COMPACT);
table.addHeaders("Name", "Age", "Status")
    .addRows("Alice", "25", "Active")
    .addRows("Bob", "30", "Inactive")
    .render();
```

## Table Manipulation

Tables support dynamic updates after creation:

### Update a Cell
```java
Table table = Clique.table(TableType.DEFAULT)
    .addHeaders("Name", "Age", "Status")
    .addRows("Alice", "25", "Active")
    .addRows("Bob", "30", "Inactive");

// Update a specific cell (row 1, column 2)
table.updateCell(1, 2, "Active");
```

### Remove a Cell
```java
// Remove a specific cell (replaces with null replacement)
table.removeCell(1, 1);
```

### Remove a Row
```java
// Remove an entire row (cannot remove headers at row 0)
table.removeRow(1);
```

### Get Table as String
```java
// Get table as string instead of printing
String tableString = table.buildTable();
System.out.println(tableString);
```

## Table Configuration

Use `TableConfiguration` to customize table appearance and behavior.

**Note:** Markup parsing is enabled by default.

### Basic Configuration
```java
TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .alignment(CellAlign.CENTER)  // Center all cells
    .padding(2)                    // Add 2 spaces padding
    .build();

Table table = Clique.table(TableType.DEFAULT, config)
    .addHeaders("Name", "Age", "Class")
    .addRows("John", "25", "Class A")
    .render();
```

### Configuration Options

#### Alignment

Control how content is aligned within cells:
```java
TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .alignment(CellAlign.CENTER)           // Center everything
    .columnAlignment(0, CellAlign.LEFT)    // Column 0 left-aligned
    .columnAlignment(2, CellAlign.RIGHT)   // Column 2 right-aligned
    .build();
```

**Note:** Column alignment always takes precedence over table alignment.

Available alignments:
- `CellAlign.LEFT` - Left-aligned (default)
- `CellAlign.CENTER` - Centered
- `CellAlign.RIGHT` - Right-aligned

#### Padding

Add whitespace around cell content to prevent cramping:
```java
TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .padding(3)  // 3 spaces on each side
    .build();
```

#### Border Styling

Style table borders with different colors:
```java
BorderStyle style = BorderStyle.immutableBuilder()
    .horizontalBorderStyles(ColorCode.CYAN)
    .verticalBorderStyles(ColorCode.MAGENTA)
    .edgeBorderStyles(ColorCode.YELLOW)
    .build();

TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .borderStyle(style)
    .build();

Table table = Clique.table(TableType.BOX_DRAW, config);
```

#### Custom Parser

Provide a custom configured parser for markup processing:
```java
ParserConfiguration parserConfig = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')
    .build();

TableConfiguration config = TableConfiguration
    .immutableBuilder()
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

TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .columnAlignment(0, CellAlign.LEFT)
    .borderStyle(style)
    .parser(Clique.parser())
    .alignment(CellAlign.CENTER)
    .padding(2)
    .build();

Table table = Clique.table(TableType.MARKDOWN, config)
    .addHeaders("[green, bold]Name[/]", "[green, bold]Age[/]", "[green, bold]Class[/]")
    .addRows("[red]John[/]", "25", "Class A")
    .addRows("[red]Doe[/]", "26", "Class B");

table.render();
```

## Null Handling

When cells are null or removed, Clique replaces them with a configurable value:
```java
TableConfiguration config = TableConfiguration.immutableBuilder()
    .nullReplacement("N/A")  // Default is empty string
    .build();

Table table = Clique.table(TableType.DEFAULT, config);
table.addHeaders("Name", "Age", "City")
    .addRows("Alice", null, "NYC");  // null becomes "N/A"

table.render();
```

This is especially useful when you have incomplete data or when using `removeCell()`.

## Customizable Tables

Customizable tables let you modify edges, vertical lines, and horizontal lines. Currently, only the `DEFAULT` table type supports customization.

### Basic Customization
```java
// Without configuration
Clique.customizableTable(TableType.DEFAULT)
    .customizeEdge('+')
    .customizeHorizontalLine('=')
    .customizeVerticalLine('|')
    .addHeaders("Col1", "Col2")
    .addRows("A", "B")
    .render();
```

### With Configuration
```java
TableConfiguration config = TableConfiguration
    .immutableBuilder()
    .padding(2)
    .build();

Clique.customizableTable(TableType.DEFAULT, config)
    .customizeEdge('*')
    .customizeHorizontalLine('-')
    .customizeVerticalLine('|')
    .addHeaders("Name", "Age")
    .addRows("Alice", "25")
    .render();
```

### Customization Methods

- `customizeEdge(char)` - Change corner/intersection characters
- `customizeHorizontalLine(char)` - Change horizontal border character
- `customizeVerticalLine(char)` - Change vertical border character

## Using Markup in Tables

Tables automatically parse markup tags when you add content:
```java
Table table = Clique.table(TableType.BOX_DRAW)
    .addHeaders(
        "[cyan, bold]Product[/]",
        "[cyan, bold]Price[/]",
        "[cyan, bold]Stock[/]"
    )
    .addRows(
        "[yellow]Widget[/]",
        "[green]$19.99[/]",
        "[red, bold]Low[/]"
    )
    .addRows(
        "[yellow]Gadget[/]",
        "[green]$29.99[/]",
        "In Stock"
    );

table.render();
```

## Things to Watch Out For

- **Emojis** will mess with width calculations. Try to avoid using them in tables.
- **Column alignment** always overrides table-wide alignment settings.

## See Also

- [Markup Reference](markup-reference.md) - Styling options for table content
- [Parser Documentation](parser.md) - How markup parsing works