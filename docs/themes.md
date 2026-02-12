# Themes

Clique supports pre-built color themes that you can use in your CLI applications. Themes provide carefully curated color palettes with consistent naming conventions.

## Terminal Requirements
Themes require truecolor (24-bit color) terminal support to display properly. Most modern terminals (iTerm2, Alacritty, Kitty, Windows Terminal, GNOME Terminal) support this by default. If colors don't look right, set `COLORTERM=truecolor` in your shell profile. See the [Custom Themes Guide](build-your-own-theme.md#terminal-requirements) for detailed setup instructions.

## Available Themes

Clique comes with popular terminal color schemes:

- **catppuccin-mocha** - Pastel dark theme with soothing colors
- **catppuccin-latte** - Pastel light theme variant
- **dracula** - Iconic purple-accented dark theme
- **gruvbox-dark** - Retro warm dark palette
- **gruvbox-light** - Retro warm light palette
- **nord** - Cool arctic-inspired color scheme
- **tokyo-night** - Modern purple-blue dark theme

### Installation

To use pre-built themes, you need both the core Clique library and the clique-themes package.

### Maven
```xml
<dependencies>
<!-- Core Clique library -->
    <dependency>
        <groupId>io.github.kusoroadeolu</groupId>
        <artifactId>clique-core</artifactId>
        <version>3.0.0</version>
    </dependency>
    
    <!-- Pre-built themes -->
    <dependency>
        <groupId>io.github.kusoroadeolu</groupId>
        <artifactId>clique-themes</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle
```gradle
dependencies {
    implementation 'com.github.kusoroadeolu:clique-core:3.0.0'
    implementation 'com.github.kusoroadeolu:clique-themes:1.0.0'
}
```

## Quick Start

### Using a Single Theme

Register and use a theme in your application:

```java
import io.github.kusoroadeolu.clique.Clique;

public class App {
    public static void main(String[] args) {
        // Register the theme you want to use
        Clique.registerTheme("catppuccin-mocha");

        // Use theme colors in your markup
        Clique.parser().print("[ctp_mauve]Styled with Catppuccin![/]");
        Clique.parser().print("[ctp_red]Error message[/]");
        Clique.parser().print("[bg_ctp_blue, ctp_text]Info box[/]");
    }
}
```

### Using Multiple Themes

Register all available themes at once:

```java
// Register every theme
Clique.registerAllThemes();

// Mix and match across themes
Clique.parser().print("[ctp_mauve]Catppuccin[/] and [tokyo_cyan]Tokyo Night[/]");
Clique.parser().print("[nord_frost0]Nord[/] meets [drac_pink]Dracula[/]");
```

## Theme Color Reference

### Catppuccin (Mocha & Latte)

Both variants share the same color names but with different RGB values:

**Accent Colors:**
- `ctp_rosewater`, `ctp_flamingo`, `ctp_pink`, `ctp_mauve`
- `ctp_red`, `ctp_maroon`, `ctp_peach`, `ctp_yellow`
- `ctp_green`, `ctp_teal`, `ctp_sky`, `ctp_sapphire`
- `ctp_blue`, `ctp_lavender`

**Surface Colors:**
- `ctp_text`, `ctp_subtext1`, `ctp_subtext0`
- `ctp_overlay2`, `ctp_overlay1`, `ctp_overlay0`
- `ctp_surface2`, `ctp_surface1`, `ctp_surface0`
- `ctp_base`, `ctp_mantle`, `ctp_crust`

**Example:**
```java
Clique.parser().print("[ctp_mauve, bold]Heading[/]");
Clique.parser().print("[ctp_red]Error:[/] [ctp_text]Something went wrong[/]");
Clique.parser().print("[bg_ctp_surface0, ctp_blue] INFO [/] Starting process...");
```

### Dracula

**Standard Colors:**
- `drac_black`, `drac_red`, `drac_green`, `drac_yellow`
- `drac_blue`, `drac_magenta`, `drac_cyan`, `drac_white`

**Bright Colors (prefixed with `*`):**
- `*drac_black`, `*drac_red`, `*drac_green`, `*drac_yellow`
- `*drac_blue`, `*drac_magenta`, `*drac_cyan`, `*drac_white`

**Example:**
```java
Clique.parser().print("[drac_magenta]♦[/] [*drac_white, bold]Dracula Theme[/]");
Clique.parser().print("[drac_red]✗[/] Failed | [drac_green]✓[/] Success");
```

### Gruvbox (Dark & Light)

Both variants use the same color names:

**Primary Colors:**
- `gb_red`, `gb_green`, `gb_yellow`, `gb_blue`
- `gb_purple`, `gb_aqua`, `gb_gray`, `gb_orange`

**Bright Variants (prefixed with `*`):**
- `*gb_red`, `*gb_green`, `*gb_yellow`, `*gb_blue`
- `*gb_purple`, `*gb_aqua`, `*gb_gray`, `*gb_orange`

**Background/Foreground Shades:**
- Backgrounds: `gb_bg`, `gb_bg0_h`, `gb_bg0_s`, `gb_bg1`, `gb_bg2`, `gb_bg3`, `gb_bg4`
- Foregrounds: `gb_fg`, `gb_fg0`, `gb_fg1`, `gb_fg2`, `gb_fg3`, `gb_fg4`

**Example:**
```java
Clique.parser().print("[gb_orange, bold]Warning:[/] [gb_fg]Check configuration[/]");
Clique.parser().print("[bg_gb_bg1, gb_aqua] → [/] [gb_yellow]Processing...[/]");
```

### Nord

**Polar Night (Dark Backgrounds):**
- `nord_polar0`, `nord_polar1`, `nord_polar2`, `nord_polar3`

**Snow Storm (Light Foregrounds):**
- `nord_snow`

**Frost (Blues/Cyans):**
- `nord_frost0`, `nord_frost1`, `nord_frost2`, `nord_frost3`

**Aurora (Accent Colors):**
- `nord_red`, `nord_orange`, `nord_yellow`, `nord_green`, `nord_purple`

**Example:**
```java
Clique.parser().print("[nord_frost2]❄[/] [nord_snow]Nord Theme[/]");
Clique.parser().print("[bg_nord_polar1, nord_frost0] INFO [/] [nord_snow]Cool and minimal[/]");
```

### Tokyo Night

**Standard Colors:**
- `tokyo_black`, `tokyo_red`, `tokyo_green`, `tokyo_yellow`
- `tokyo_blue`, `tokyo_magenta`, `tokyo_cyan`, `tokyo_white`

**Bright Colors (prefixed with `*`):**
- `*tokyo_black`, `*tokyo_red`, `*tokyo_green`, `*tokyo_yellow`
- `*tokyo_blue`, `*tokyo_magenta`, `*tokyo_cyan`, `*tokyo_white`

**Theme Colors:**
- `tokyo_bg`, `tokyo_fg`

**Example:**
```java
Clique.parser().print("[tokyo_magenta]◆[/] [*tokyo_white, bold]Tokyo Night[/]");
Clique.parser().print("[tokyo_cyan]›[/] [tokyo_fg]Modern and clean[/]");
```

## Background Colors

Every theme color has a corresponding background variant with the `bg_` prefix:

```java
// Foreground and background combinations
Clique.parser().print("[bg_ctp_mauve, ctp_base]Inverted[/]");
Clique.parser().print("[bg_tokyo_blue, tokyo_bg]Highlighted[/]");
Clique.parser().print("[bg_nord_frost2, nord_polar0]Arctic vibes[/]");
```

## Combining with Built-in Styles

Theme colors work seamlessly with Clique's text styling:

```java
// Bold, underline, italic, etc.
Clique.parser().print("[ctp_mauve, bold, ul]Important Heading[/]");
Clique.parser().print("[gruvbox_orange, bg_gruvbox_bg1, bold] WARNING [/]");
```

## Best Practices

### 1. Choose One or Two Primary Themes

Stick to a single or dual theme structure for consistency:

```java
Clique.registerTheme("catppuccin-mocha");
Clique.parser().print("[ctp_red]Error[/]");
Clique.parser().print("[ctp_green]Success[/]");
Clique.parser().print("[ctp_yellow]Warning[/]");
```

### 2. Use Semantic Color Naming

Create semantic aliases for your theme colors by creating wrapper styles:

```java
Clique.registerTheme("nord");

// Create semantic color wrappers
public class AppStyles {
    public static final AnsiCode ERROR = new RGBColor(191, 97, 106, false);   // nord_red
    public static final AnsiCode SUCCESS = new RGBColor(163, 190, 140, false); // nord_green
    public static final AnsiCode INFO = new RGBColor(136, 192, 208, false);    // nord_frost2
}

// Register semantic names
Clique.registerStyle("error", AppStyles.ERROR);
Clique.registerStyle("success", AppStyles.SUCCESS);
Clique.registerStyle("info", AppStyles.INFO);

// Use semantic names in your app
Clique.parser().print("[error]Failed to connect[/]");
Clique.parser().print("[success]Connection established[/]");
```

### 3. Respect Light/Dark Variants

Choose the appropriate theme variant for your target environment:

```java
// For dark terminals
Clique.registerTheme("catppuccin-mocha");
Clique.registerTheme("gruvbox-dark");

// For light terminals
Clique.registerTheme("catppuccin-latte");
Clique.registerTheme("gruvbox-light");
```

### 4. Use Surface Colors for UI Elements

Theme surface colors are designed for backgrounds and subtle UI elements:

```java
// Catppuccin UI boxes
Clique.parser().print("[bg_ctp_surface0, ctp_text] Package Manager [/]");
Clique.parser().print("[ctp_overlay0]├──[/] [ctp_blue]Installing dependencies...[/]");
Clique.parser().print("[ctp_overlay0]└──[/] [ctp_green]✓ Complete[/]");
```

## Finding Available Themes
List all themes programmatically:

```java
List<CliqueTheme> themes = Clique.discoverThemes();
themes.forEach(theme -> {
        System.out.println("Theme: " + theme.themeName());
        });
```

Check if a specific theme exists:

```java
Clique.findTheme("tokyo-night").ifPresentOrElse(
        theme -> {
        theme.register();
        System.out.println("Tokyo Night loaded!");
    },
            () -> System.out.println("Theme not found")
);
```

## Creating Custom Themes
Want to create your own theme? Check out the [Custom Themes Guide](build-your-own-theme.md) for details on building custom themes that integrate with Clique's theme system.

## See Also

- [Markup Reference](markup-reference.md) - Built-in color and style tags
- [Custom Themes Guide](build-your-own-theme.md) - Create your own themes
- [Parser Documentation](parser.md) - How to use the parser