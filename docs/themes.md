# Themes

Clique supports pre-built color themes that you can use in your CLI applications. Themes provide carefully curated color palettes with consistent naming conventions.

## Terminal Requirements
For themes to display correctly with their full color palette, your terminal must support truecolor (24-bit color). Most modern terminals support this by default, but you may need to enable it:

- Check support: Run echo $COLORTERM - it should output truecolor or 24bit
- Enable truecolor: Set the environment variable COLORTERM=truecolor in your shell profile
- Terminal compatibility: Ensure your terminal emulator supports 24-bit color (most modern terminals like iTerm2, Alacritty, Kitty, Windows Terminal, and recent versions of GNOME Terminal do)
Without truecolor support, themes may appear with reduced color accuracy or fall back to the nearest 256-color approximation.

## Available Themes

Clique comes with popular terminal color schemes:

- **catppuccin-mocha** - Pastel dark theme with soothing colors
- **catppuccin-latte** - Pastel light theme variant
- **dracula** - Iconic purple-accented dark theme
- **gruvbox-dark** - Retro warm dark palette
- **gruvbox-light** - Retro warm light palette
- **nord** - Cool arctic-inspired color scheme
- **tokyo-night** - Modern purple-blue dark theme

## Quick Start

### Using a Single Theme

Register and use a theme in your application:

```java
import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.themes.CliqueThemes;

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

### Manual Registration

You can also instantiate and register themes manually:

```java
import com.github.kusoroadeolu.cliquethemes.CatppuccinMochaTheme;

var theme = new CatppuccinMochaTheme();
theme.register();

Clique.parser().print("[ctp_mauve]Now available![/]");
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
- `gruvbox_red`, `gruvbox_green`, `gruvbox_yellow`, `gruvbox_blue`
- `gruvbox_purple`, `gruvbox_aqua`, `gruvbox_gray`, `gruvbox_orange`

**Bright Variants (prefixed with `*`):**
- `*gruvbox_red`, `*gruvbox_green`, `*gruvbox_yellow`, `*gruvbox_blue`
- `*gruvbox_purple`, `*gruvbox_aqua`, `*gruvbox_gray`, `*gruvbox_orange`

**Background/Foreground Shades:**
- Backgrounds: `gruvbox_bg`, `gruvbox_bg0_h`, `gruvbox_bg0_s`, `gruvbox_bg1`, `gruvbox_bg2`, `gruvbox_bg3`, `gruvbox_bg4`
- Foregrounds: `gruvbox_fg`, `gruvbox_fg0`, `gruvbox_fg1`, `gruvbox_fg2`, `gruvbox_fg3`, `gruvbox_fg4`

**Example:**
```java
Clique.parser().print("[gruvbox_orange, bold]Warning:[/] [gruvbox_fg]Check configuration[/]");
Clique.parser().print("[bg_gruvbox_bg1, gruvbox_aqua] → [/] [gruvbox_yellow]Processing...[/]");
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
Clique.parser().print("[drac_red, italic]Error message[/]");
Clique.parser().print("[tokyo_cyan, bold]● [/][tokyo_fg]Status: Active[/]");

// Multiple styles
Clique.parser().print("[gruvbox_orange, bg_gruvbox_bg1, bold] WARNING [/]");
```

## Best Practices

### 1. Choose One or Two Theme Per Application

Stick to a single or double themes for consistency:

```java
// Good - consistent theme
Clique.registerTheme("catppuccin-mocha");
Clique.parser().print("[ctp_red]Error[/]");
Clique.parser().print("[ctp_green]Success[/]");
Clique.parser().print("[ctp_yellow]Warning[/]");

// Avoid - mixing themes randomly
Clique.parser().print("[ctp_red]Error[/]");
Clique.parser().print("[tokyo_green]Success[/]");  // Different theme
```

### 2. Use Semantic Color Naming

Create semantic aliases for your theme colors:

```java
Clique.registerTheme("nord");

// Create semantic mappings
Clique.registerStyle("error", StyleMaps.GLOBAL_CUSTOM_CODES.get("nord_red"));
Clique.registerStyle("success", StyleMaps.GLOBAL_CUSTOM_CODES.get("nord_green"));
Clique.registerStyle("info", StyleMaps.GLOBAL_CUSTOM_CODES.get("nord_frost2"));

// Use semantic names
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

Want to create your own theme? Check out the [Parser Composability Guide](parser-compose.md) for details on building custom themes that integrate with Clique's theme system.

## See Also

- [Markup Reference](markup-reference.md) - Built-in color and style tags
- [Custom Themes Reference](build-your-own-theme.md) - Built-in color and style tags
- [Parser Documentation](parser.md) - How to use the parser
- [Parser Composability](parser-compose.md) - Create custom themes and colors