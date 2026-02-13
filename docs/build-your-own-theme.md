# Creating Custom Themes

Learn how to build and distribute your own color themes for Clique.

## Overview

Custom themes allow you to package color palettes as reusable, discoverable components. Once created, themes can be:
- Automatically discovered via Java's ServiceLoader
- Registered by name
- Shared across projects
- Distributed as separate libraries

## Terminal Requirements
For themes to display correctly with their full color palette, your terminal must support truecolor (24-bit color). Most modern terminals support this by default, but you may need to enable it:

- Check support: Run echo $COLORTERM - it should output truecolor or 24bit
- Enable truecolor: Set the environment variable COLORTERM=truecolor in your shell profile
- Terminal compatibility: Ensure your terminal emulator supports 24-bit color (most modern terminals like iTerm2, Alacritty, Kitty, Windows Terminal, and recent versions of GNOME Terminal do)
  Without truecolor support, themes may appear with reduced color accuracy or fall back to the nearest 256-color approximation.

## Setup
To create custom themes, you only need the clique-spi library:

### Maven
```xml
<dependency>
    <groupId>io.github.kusoroadeolu</groupId>
    <artifactId>clique-spi</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle
```gradle
dependencies {
    implementation 'io.github.kusoroadeolu:clique-core:3.0.0'
}
```

**Optional:** If you want to reference or test against the pre-built themes, you can also include `clique-themes`:
```xml
<dependency>
    <groupId>io.github.kusoroadeolu</groupId>
    <artifactId>clique-themes</artifactId>
    <version>${version}</version>
</dependency>
```


## Basic Theme Structure
All themes implement the `CliqueTheme` interface from the `Clique` library:

```java
public class MyCustomTheme implements CliqueTheme {

    @Override
    public String themeName() {
        return "my-theme";
    }

    @Override
    public Map<String, AnsiCode> styles() {
        return Map.ofEntries(
                Map.entry("mytheme_primary", new CustomAnsiCode("\u001B[38;2;66;135;245m")),
                Map.entry("mytheme_accent", new CustomAnsiCode("\u001B[38;2;156;39;176m")),
                Map.entry("bg_mytheme_primary", new CustomAnsiCode("\u001B[48;2;66;135;245m"))
        );
    }

    private record CustomAnsiCode(String code) implements AnsiCode {
        @Override
        public String toString() {
            return code;
        }
    }
}
```

## Step-by-Step Guide

### 1. Create the Theme Class

Start by implementing the `CliqueTheme` interface:

```java
public class SolarizedDarkTheme implements CliqueTheme {
    
    @Override
    public String themeName() {
        return "solarized-dark";
    }
    
    @Override
    public Map<String, AnsiCode> styles() {
        return Map.ofEntries(
            // Base colors
            Map.entry("sol_base03", rgb(0, 43, 54)),
            Map.entry("sol_base02", rgb(7, 54, 66)),
            Map.entry("sol_base01", rgb(88, 110, 117)),
            Map.entry("sol_base00", rgb(101, 123, 131)),
            Map.entry("sol_base0", rgb(131, 148, 150)),
            Map.entry("sol_base1", rgb(147, 161, 161)),
            Map.entry("sol_base2", rgb(238, 232, 213)),
            Map.entry("sol_base3", rgb(253, 246, 227)),
            
            // Accent colors
            Map.entry("sol_yellow", rgb(181, 137, 0)),
            Map.entry("sol_orange", rgb(203, 75, 22)),
            Map.entry("sol_red", rgb(220, 50, 47)),
            Map.entry("sol_magenta", rgb(211, 54, 130)),
            Map.entry("sol_violet", rgb(108, 113, 196)),
            Map.entry("sol_blue", rgb(38, 139, 210)),
            Map.entry("sol_cyan", rgb(42, 161, 152)),
            Map.entry("sol_green", rgb(133, 153, 0)),
            
            // Backgrounds
            Map.entry("bg_sol_base03", rgb(0, 43, 54, true)),
            Map.entry("bg_sol_yellow", rgb(181, 137, 0, true)),
            Map.entry("bg_sol_orange", rgb(203, 75, 22, true)),
            Map.entry("bg_sol_red", rgb(220, 50, 47, true))
            // ... add more backgrounds as needed
        );
    }
    
    public String author(){
        return "someone";
    }
    
    public String url(){
        return "url";
    }
    
    // Helper method to create RGB colors
    private static AnsiCode rgb(int r, int g, int b) {
        return rgb(r, g, b, false);
    }
    
    private static AnsiCode rgb(int r, int g, int b, boolean background) {
        int type = background ? 48 : 38;
        String code = String.format("\u001B[%d;2;%d;%d;%dm", type, r, g, b);
        return new RGBAnsiCode(code);
    }
    
    private record RGBAnsiCode(String code) implements AnsiCode {
        @Override
        public String toString() {
            return code;
        }
    }
}
```

### 2. Make Your Theme Discoverable

Create a service provider configuration file so Java's ServiceLoader can find your theme.

**File location:** `src/main/resources/META-INF/services/io.github.kusoroadeolu.clique.spi.CliqueTheme`

**File content:**
```
com.example.themes.SolarizedDarkTheme
```

If you have multiple themes in your package, list them all:
```
com.example.themes.SolarizedDarkTheme
com.example.themes.SolarizedLightTheme
com.example.themes.MonokaiTheme
```

#### Multi-module projects (JPMS)

If you're using Java Platform Module System (JPMS), you'll also need to declare your theme services in your `module-info.java` file at the root of your module:

**File location:** `src/main/java/module-info.java`
```java
module my.themes {
    requires clique.spi;
    uses io.github.kusoroadeolu.clique.spi.CliqueTheme;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme
            with com.example.themes.SolarizedDarkTheme,
                    com.example.themes.SolarizedLightTheme,
                    com.example.themes.MonokaiTheme;
}
```

**Note:** You still need the `META-INF/services` file from step 2 for non-modular classpath scenarios.

### 3. Use Your Theme

Once registered as a service, your theme is automatically discoverable:

```java
public class App {
    public static void main(String[] args) {
        // Register by name
        Clique.registerTheme("solarized-dark");
        
        // Use your theme colors
        Clique.parser().print("[sol_blue]Hello, Solarized![/]");
        Clique.parser().print("[sol_red]Error:[/] [sol_base0]Something went wrong[/]");
    }
}
```

## Proposed Naming Conventions

Follow these conventions for consistency:

### Color Names

Use descriptive, lowercase names with underscores:

```java
// Good
"mytheme_primary"
"mytheme_accent"
"mytheme_error"

// Avoid
"MyThemePrimary"  // Wrong case
"primary"         // Too generic, conflicts with other themes
"mytheme-accent"  // Use underscores, not hyphens
```

### Background Colors

Always prefix background colors with `bg_`:

```java
Map.entry("mytheme_blue", ...),      // Foreground
Map.entry("bg_mytheme_blue", ...),   // Background
```

### Bright/Dark Variants

Use prefixes to indicate variants:

```java
// Standard colors
"mytheme_red"
"mytheme_blue"

// Bright variants
"*mytheme_red"
"*mytheme_blue"

// Dark variants (if applicable)
"dark_mytheme_red"
"dark_mytheme_blue"
```

### Theme Name

Use lowercase with hyphens for the theme name:

```java
@Override
public String themeName() {
    return "my-awesome-theme";  // ✓ Good
}
```

Try avoiding using hyphens or camelcase for theme names

## Testing Your Theme

Create a simple test to verify all colors render correctly:

```java
public class ThemeTest {
    public static void main(String[] args) {
        var theme = new MyCustomTheme();
        theme.register();
        
        System.out.println("Testing theme: " + theme.themeName());
        System.out.println();
        
        // Test each color
        theme.styles().forEach((name, code) -> {
            if (name.startsWith("bg_")) {
                // Background colors
                Clique.parser().print("[" + name + ", black] " + name + " [/]");
            } else {
                // Foreground colors
                Clique.parser().print("[" + name + "]" + name + "[/]");
            }
        });
    }
}
```

## Distributing Your Theme

### As a Separate Library

Package your theme as a standalone JAR:

**Project structure:**
```
my-clique-themes/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/themes/
│       │       ├── MyTheme1.java
│       │       └── MyTheme2.java
│       └── resources/
│           └── META-INF/
│               └── services/
│                   └── io.github.kusoroadeolu.clique.spi.CliqueTheme
└── pom.xml (or build.gradle)
```

**Maven dependency:**
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-clique-themes</artifactId>
    <version>1.0.0</version>
</dependency>
```

Users can then discover and use your themes:
```java
Clique.registerAllThemes();  // Auto-discovers your themes
Clique.registerTheme("my-theme-1");
```

### In the Same Project
If your theme is for internal use only, you can skip the ServiceLoader registration and register manually:

```java
public class AppTheme implements CliqueTheme {
    // ... implementation
}

// In your main class
var theme = new AppTheme();
theme.register();
```

## Best Practices

### 1. Provide Complete Palettes

Include both foreground and background variants for all your colors.

### 2. Include Semantic Colors

Add semantic color names for common use cases:

```java
Map.entry("theme_error", rgb(220, 50, 47)),
Map.entry("theme_success", rgb(133, 153, 0)),
Map.entry("theme_warning", rgb(181, 137, 0)),
Map.entry("theme_info", rgb(38, 139, 210))
```

### 3. Pre-compute ANSI Codes

Compute ANSI codes once in the constructor or initialization, not in `toString()`:

```java
// Good - computed once
private record CustomAnsiCode(String code) implements AnsiCode {
    @Override
    public String toString() {
        return code;
    }
}

// Bad - computes every time
private static class CustomAnsiCode implements AnsiCode {
    private final int r, g, b;
    
    @Override
    public String toString() {
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);  // Recomputes!
    }
}
```

## Common Pitfalls

### Missing Service Provider Configuration

Theme isn't discovered by `CliqueThemeLoader.discover()`, so always ensure you have the service provider file at:
```
src/main/resources/META-INF/services/io.github.kusoroadeolu.clique.spi.CliqueTheme
```

### Forgetting toString() Implementation

Colors don't appear, or you see object addresses like `CustomAnsiCode@1a2b3c4d`. Therefore, always implement `toString()` in your `AnsiCode` implementation to return the actual ANSI escape code string:
```java
private record CustomAnsiCode(String code) implements AnsiCode {
    @Override
    public String toString() {  // This is essential!
        return code;
    }
}
```

### Conflicting Color Names
 Colors from different themes could override each other, hence always prefix your color names with your theme identifier:
```java
// Good
"mytheme_red"
"mytheme_blue"

// Bad - can conflict with other themes
"red"
"blue"
```

## Example: Complete Theme

Here's a complete, production-ready theme implementation:

```java
package com.example.themes;

import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.spi.CliqueTheme;

import java.util.HashMap;
import java.util.Map;

/**
 * A business based theme with corporate colors.
 */
public class CorporateTheme implements CliqueTheme {
    
    @Override
    public String themeName() {
        return "corporate";
    }
    
    @Override
    public Map<String, AnsiCode> styles() {
        Map<String, AnsiCode> colors = new HashMap<>();
        
        // Brand colors
        addColor(colors, "corp_navy", "#003366");
        addColor(colors, "corp_gold", "#FFB81C");
        addColor(colors, "corp_slate", "#54585A");
        
        // Semantic colors
        addColor(colors, "corp_success", "#2E7D32");
        addColor(colors, "corp_error", "#C62828");
        addColor(colors, "corp_warning", "#F57C00");
        addColor(colors, "corp_info", "#0277BD");
        
        // Neutral colors
        addColor(colors, "corp_text", "#212121");
        addColor(colors, "corp_text_light", "#757575");
        addColor(colors, "corp_bg", "#F5F5F5");
        addColor(colors, "corp_bg_dark", "#E0E0E0");
        
        return colors;
    }
    
    private void addColor(Map<String, AnsiCode> map, String name, String hex) {
        map.put(name, hexToAnsi(hex, false));
        map.put("bg_" + name, hexToAnsi(hex, true));
    }
    
    private AnsiCode hexToAnsi(String hex, boolean background) {
        hex = hex.startsWith("#") ? hex.substring(1) : hex;
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        
        int type = background ? 48 : 38;
        String code = String.format("\u001B[%d;2;%d;%d;%dm", type, r, g, b);
        return new CustomAnsiCode(code);
    }
    
    private record CustomAnsiCode(String code) implements AnsiCode {
        @Override
        public String toString() {
            return code;
        }
    }
}
```

**Usage:**
```java
Clique.registerTheme("corporate");
Clique.parser().print("[corp_navy, bold]QUARTERLY REPORT[/]");
Clique.parser().print("[corp_success]✓[/] Revenue: [corp_gold]$2.5M[/]");
Clique.parser().print("[corp_error]✗[/] Expenses: [corp_text]$1.8M[/]");
```

## See Also

- [Themes Documentation](themes.md) - Using pre-built themes
- [Parser Composability](parser-compose.md) - Advanced custom ANSI codes
- [Markup Reference](markup-reference.md) - Using theme colors in markup