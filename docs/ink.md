# Ink

`Ink` is a lightweight, functional, chainable ANSI string builder — similar in spirit to [Chalk](https://github.com/chalk/chalk). It applies colors and styles directly as ANSI escape sequences, with no markup parser involved.

Each method returns a **new `Ink` instance**, leaving the original unchanged. Call `on()` at the end of the chain to produce the final styled string.

## Getting Started

Obtain an `Ink` instance via `Clique.ink()`:

```java
// Basic usage
Clique.ink().red().bold().on("Error")

// With a custom StyleContext for registered theme styles
Clique.ink(myContext).of("ctp_mauve").bold().on("Hello")
```

To reuse a base style across multiple values:

```java
Ink bold = Clique.ink().bold();

bold.red().on("error");   // bold + red
bold.yellow().on("warn"); // bold + yellow — original `bold` instance untouched
```

## API Reference

### Terminal Method

#### `on(Object value) → String`

Applies the accumulated styles to `value` and returns the styled string. Calls `toString()` on the value and appends a reset sequence at the end.

```java
Clique.ink().red().bold().on("Error")   // styled string
Clique.ink().on("plain")                // no styles — returned as-is
Clique.ink().green().on(42)             // toString() called on numeric values
```

---

### Named Style Lookup

#### `of(String name) → Ink`

Adds a named style looked up from the instance's `StyleContext` and `PredefinedStyleContext`. Useful for registered theme or custom styles that aren't statically knowable.

Throws `UndefinedStyleException` if the name isn't found in either context.

```java
Clique.ink().of("ctp_mauve").bold().on("Hello")
```

---

### Foreground Colors

| Method          | Color          |
|-----------------|----------------|
| `black()`       | Black          |
| `red()`         | Red            |
| `green()`       | Green          |
| `yellow()`      | Yellow         |
| `blue()`        | Blue           |
| `magenta()`     | Magenta        |
| `cyan()`        | Cyan           |
| `white()`       | White          |
| `brightBlack()` | Bright Black   |
| `brightRed()`   | Bright Red     |
| `brightGreen()` | Bright Green   |
| `brightYellow()`| Bright Yellow  |
| `brightBlue()`  | Bright Blue    |
| `brightMagenta()`| Bright Magenta|
| `brightCyan()`  | Bright Cyan    |
| `brightWhite()` | Bright White   |

#### `rgb(int red, int green, int blue) → Ink`

Applies a 24-bit foreground color using the provided RGB components. Requires truecolor terminal support.

```java
Clique.ink().rgb(255, 94, 77).bold().on("Hot red")
```

---

### Background Colors

| Method            | Color              |
|-------------------|--------------------|
| `bgBlack()`       | Black background   |
| `bgRed()`         | Red background     |
| `bgGreen()`       | Green background   |
| `bgYellow()`      | Yellow background  |
| `bgBlue()`        | Blue background    |
| `bgMagenta()`     | Magenta background |
| `bgCyan()`        | Cyan background    |
| `bgWhite()`       | White background   |
| `brightBgBlack()` | Bright Black BG    |
| `brightBgRed()`   | Bright Red BG      |
| `brightBgGreen()` | Bright Green BG    |
| `brightBgYellow()`| Bright Yellow BG   |
| `brightBgBlue()`  | Bright Blue BG     |
| `brightBgMagenta()`| Bright Magenta BG |
| `brightBgCyan()`  | Bright Cyan BG     |
| `brightBgWhite()` | Bright White BG    |

#### `bgRgb(int red, int green, int blue) → Ink`

Applies a 24-bit background color using the provided RGB components. Requires truecolor terminal support.

```java
Clique.ink().white().bgRgb(30, 30, 30).on("Dark background")
```

---

### Text Styles

| Method              | Effect                              |
|---------------------|-------------------------------------|
| `bold()`            | Bold / emphasized text              |
| `dim()`             | Reduced brightness                  |
| `italic()`          | Italic text                         |
| `underline()`       | Underlined text                     |
| `doubleUnderline()` | Double underlined text              |
| `strikethrough()`   | Strikethrough text                  |
| `reverseVideo()`    | Swaps foreground and background     |
| `invisible()`       | Hidden text                         |

---

## Combining Styles

Any number of methods can be chained together:

```java
// Color + style
Clique.ink().red().bold().on("Error")

// Foreground + background + style
Clique.ink().white().bgRed().bold().on("Alert")

// RGB foreground + style
Clique.ink().rgb(255, 94, 77).italic().on("Custom color")

// Multiple styles
Clique.ink().bold().underline().italic().on("Everything")
```

---

## Using with a StyleContext

Pass a `StyleContext` at construction to make registered theme or custom styles available via `of()`:

```java
StyleContext ctx = StyleContext.builder()
    .add("ctp_mauve", myMauveCode)
    .add("ctp_sky", mySkyCode)
    .build();

Ink ink = Clique.ink(ctx);

ink.of("ctp_mauve").bold().on("Hello")
ink.of("ctp_sky").on("World")
```

Styles are resolved in this order: local `StyleContext` → `GlobalStyleRegistry` → predefined colors and styles.

---

## See Also

- [Markup Reference](markup-reference.md) — markup tag syntax for use with the parser
- [StyleBuilder Documentation](stylebuilder.md) — explicit and imperative ANSI-output builder for full strings
- [StyleContext](parser.md) — registering custom and theme styles