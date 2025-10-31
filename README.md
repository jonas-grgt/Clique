
# CLIQUE - README [UNRELEASED]
## INTRODUCTION 
Clique is my mini CLI framework aimed at beautifying CLI applications in Java.

## Why Clique?
Raw ANSI codes are ugly and hard to read plus Java doesn't have great CLI tooling for ANSI codes:
```java
System.out.println("\u001B[31m\u001B[1mError:\u001B[0m File not found");
```

Clique makes it clean:
```java
Clique.parser().print("[red, bold]Error:[/] File not found");
```

### IMPLEMENTATION

#### StyleBuilder
Clique uses a fluent builder pattern to chain styled strings with each other. If you enjoy using the builder pattern this is for you
</br> You can append styles to a string using `append()` and print it to the terminal. This resets the terminal style after each append call
```java
Clique.styleBuilder()
      .append("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
      .append("World", ColorCode.RED, StyleCode.DIM , StyleCode.REVERSE_VIDEO)
      .print();    
```


You can append styles to strings using the `stack()` method, but it doesn't reset the terminal style. I added this for more flexibility
```java
Clique.styleBuilder()
      .stack("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
      .stack("World", ColorCode.RED, StyleCode.DIM , StyleCode.REVERSE_VIDEO)
      .print();    
```


You can apply styles to text with `format()`. This method does not reset the terminal style
```java
String styledText = Clique.styleBuilder().format("This text is red", ColorCode.RED);
```

You can apply styles to text with `formatReset()`. This method resets the terminal style

```java
String styledText = Clique.styleBuilder().formatReset("This text is red", ColorCode.RED,StyleCode.BOLD);
```

You can get the styledText after appending strings by using the `get` method
```java
String styledText = Clique.styleBuilder()
        .stack("Hello", ColorCode.BLUE, StyleCode.BOLD, StyleCode.UNDERLINE)
        .stack("World", ColorCode.RED, StyleCode.DIM)
        .get(); 
```

### Markup Parsing
Clique supports a markup parsing format for less verbose style building

- `AnsiStringParser`. This allows you to parse supported markup strings with default parsing configurations i.e. lenient parsing, a default delimiter and tags are

```java
String str = "[red, bold]Hello [blue, ul]World";
AnsiStringParser parser = Clique.parser(); 
parser.print(str); //This will print `Hello` as red and bold, reset and print `World` as blue and underlined
```

- `ParserConfiguration`. This allows you to configure your parser to enable strict parsing, set your custom delimiter or auto close tags

```java
import parser.configuration.ParserConfiguration;

String str = "[red bold]Hello[blue] World"; //Notice there are no commas as the delimiter here
ParserConfiguration configuration = ParserConfiguration
                                        .builder()
                                        .enableAutoCloseTags() //Auto closes tags for you
                                        .enableStrictParsing() //Parser will throw an error if it finds a style that does not exist
                                        .delimiter(' '); //Set the default delimiter to a space
AnsiStringParser configuredParser = Clique.parser()
        .configuration(configuration);


configuredParser.print(str);
```
Here we can see we set a custom delimiter. This allows for more flexibility for those who don't want to use the default `comma` delimiter

```java
import core.clique.Clique;

//What if we have a misplaced style or a style that doesn't exist, the parser will throw an error
String str = "[red bol]Hello[/] [blue ul]World";
AnsiStringParser parser = Clique.parser();
String parsed = parser.parse(str);
```
Here, because the style `bol` doesn't exist and strict parsing is enabled, the parser will throw an `UnidentifedStyleException` indicating the style doesn't exist.

**NOTE:** Malformed styles i.e. `[[[red]` or `[red[bold]]`might cause weird styling issues. With strict parsing enabled it will throw a parse problem exception

## Supported Markup Options
Clique supports **text color**, **background color**, and **text style** tags inside markup strings.

---

### Text Colors

Use standard or bright color names:

| Type | Example | Description |
|------|----------|------------|
| Standard | `red`, `green`, `yellow`, `blue`, `magenta`, `cyan`, `white`, `black` | Basic ANSI text colors |
| Bright | `*red`, `*green`, `*yellow`, `*blue`, `*magenta`, `*cyan`, `*white`, `*black` | Brighter versions of the standard colors |

**Example**
```java
Clique.parser().print("[red, bold]Error:[/] File not found");
Clique.parser().print("[*blue]Bright blue text[/]");
```

---

### Background Colors

Prefix color names with `bg_` for background colors.  
Use `*bg_` for bright backgrounds.

| Type | Example | Description |
|------|----------|-------------|
| Standard | `bg_red`, `bg_blue`, `bg_yellow`, `bg_white` | Standard background colors |
| Bright | `*bg_red`, `*bg_blue`, `*bg_yellow`, `*bg_white` | Bright background colors |

**Example**
```java
Clique.parser().print("[bg_red, white]Alert![/]");
Clique.parser().print("[*bg_blue, *white]Bright background[/]");
```

---

### Text Styles

Clique supports a range of ANSI text effects:

| Style | Tag | Description |
|--------|------|-------------|
| **Bold** | `bold` | Emphasizes text |
| **Dim** | `dim` | Lowers brightness |
| **Italic** | `italic` | Slants the text |
| **Underline** | `ul` | Underlines text |
| **Reverse Video** | `rv` | Swaps foreground/background colors |
| **Invisible Text** | `inv` | Hides text (useful for debugging or tricks) |
| **Reset** | `/` | Resets all styles |

**Example**
```java
Clique.parser().print("[bold, ul]Important[/] [dim]subtle note[/]");
Clique.parser().print("[rv]Inverted colors![/]");
```

---

### Quick Reference

| Category | Example Syntax | Result |
|-----------|----------------|--------|
| Text Color | `[red]Text[/]` | Red text |
| Bright Color | `[*blue]Text[/]` | Bright blue text |
| Background | `[bg_yellow, black]Text[/]` | Black text on yellow background |
| Bright Background | `[*bg_green, white]Text[/]` | White text on bright green background |
| Style | `[bold, ul, red]Text[/]` | Red, bold, and underlined |
| Reset | `[red]Text[/]` | Resets style after closing tag |


## Tables
Tables are a feature of Clique that are still in development. For a brief introduction, there are currently 3 tables
1. Default table 
2. Compact table
3. Box Draw Table

All of these tables are hidden behind the table interface and can be accessed using the `TableFactory` class. 

**Note that some things here are subject to change**

```java
TableFactory.getTable(TableType.COMPACT)
.addHeaders("Name", "Age", "Class")
.addRows("John", "25", "Class A")
.addRows("Doe", "26", "Class B")
.render();
```

## Features yet to be implemented
- Interactive options(Still considering this)
- Full terminal support for all OS's
