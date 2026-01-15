# Parser Composability

This guide covers advanced techniques for extending the parser by creating completely new ANSI codes, composing complex style combinations, and integrating external styling systems.

## Understanding AnsiCode

All styles in Clique implement the `AnsiCode` interface. This means you can create your own custom ANSI sequences and register them with the parser.

### The AnsiCode Interface
```java
public interface AnsiCode {
    String getCode();
}
```

Any class implementing this interface can be registered as a custom style.

## Creating Custom ANSI Codes

### Basic Custom Code

Create a new ANSI code from scratch:
```java
public class CustomAnsiCode implements AnsiCode {
    private final String code;
    
    public CustomAnsiCode(String ansiSequence) {
        this.code = ansiSequence;
    }
    
    @Override
    public String getCode() {
        return code;
    }
}

// Register a custom ANSI sequence
Clique.registerStyle("blink", new CustomAnsiCode("\u001B[5m"));
Clique.registerStyle("rapid-blink", new CustomAnsiCode("\u001B[6m"));

// Use it
Clique.parser().print("[blink]Blinking text![/]");
```

### 256-Color Support

Extend support to 256-color palette:
```java
public class Extended256Color implements AnsiCode {
    private final int colorNumber;
    private final boolean isBackground;
    
    public Extended256Color(int colorNumber, boolean isBackground) {
        if (colorNumber < 0 || colorNumber > 255) {
            throw new IllegalArgumentException("Color must be 0-255");
        }
        this.colorNumber = colorNumber;
        this.isBackground = isBackground;
    }
    
    @Override
    public String getCode() {
        int type = isBackground ? 48 : 38;
        return String.format("\u001B[%d;5;%dm", type, colorNumber);
    }
}

// Register 256 colors
Clique.registerStyle("orange", new Extended256Color(214, false));
Clique.registerStyle("purple", new Extended256Color(135, false));
Clique.registerStyle("bg-orange", new Extended256Color(214, true));

// Use them
Clique.parser().print("[orange]Orange text[/]");
Clique.parser().print("[purple]Purple text[/]");
Clique.parser().print("[bg-orange, black]Highlighted[/]");
```

### RGB True Color Support

Add full 24-bit RGB color support:
```java
public class RGBColor implements AnsiCode {
    private final int r, g, b;
    private final boolean isBackground;
    
    public RGBColor(int r, int g, int b, boolean isBackground) {
        this.r = clamp(r, 0, 255);
        this.g = clamp(g, 0, 255);
        this.b = clamp(b, 0, 255);
        this.isBackground = isBackground;
    }
    
    @Override
    public String getCode() {
        int type = isBackground ? 48 : 38;
        return String.format("\u001B[%d;2;%d;%d;%dm", type, r, g, b);
    }
    
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}

// Register custom RGB colors
Clique.registerStyle("coral", new RGBColor(255, 127, 80, false));
Clique.registerStyle("lavender", new RGBColor(230, 230, 250, false));
Clique.registerStyle("bg-navy", new RGBColor(0, 0, 128, true));

// Use them
Clique.parser().print("[coral]Coral colored text[/]");
Clique.parser().print("[lavender, bg-navy]Lavender on navy[/]");
```

## Composing Complex Styles

### Multi-Effect Combinations

Create pre-composed styles that combine multiple effects:
```java
public class CompositeStyle implements AnsiCode {
    private final List<AnsiCode> codes;
    
    public CompositeStyle(AnsiCode... codes) {
        this.codes = Arrays.asList(codes);
    }
    
    @Override
    public String getCode() {
        return codes.stream()
            .map(AnsiCode::getCode)
            .collect(Collectors.joining());
    }
}

// Create complex pre-composed styles
AnsiCode errorStyle = new CompositeStyle(
    ColorCode.BRIGHT_RED,
    StyleCode.BOLD,
    StyleCode.UNDERLINE
);

AnsiCode successStyle = new CompositeStyle(
    ColorCode.BRIGHT_GREEN,
    StyleCode.BOLD
);

AnsiCode headerStyle = new CompositeStyle(
    ColorCode.BRIGHT_CYAN,
    StyleCode.BOLD,
    BackgroundCode.BRIGHT_BLACK
);

// Register them
Clique.registerStyle("error", errorStyle);
Clique.registerStyle("success", successStyle);
Clique.registerStyle("header", headerStyle);

// Single tag applies all effects
Clique.parser().print("[error]Critical error occurred![/]");
Clique.parser().print("[success]Operation successful![/]");
Clique.parser().print("[header] System Status [/]");
```

### Theme-Based Composites

Create entire themes as composite styles:
```java
public class ThemeStyles {
    // Dark theme
    public static final AnsiCode DARK_HEADING = new CompositeStyle(
        ColorCode.BRIGHT_BLUE,
        StyleCode.BOLD
    );
    
    public static final AnsiCode DARK_BODY = new CompositeStyle(
        ColorCode.WHITE
    );
    
    public static final AnsiCode DARK_ACCENT = new CompositeStyle(
        ColorCode.BRIGHT_CYAN,
        StyleCode.ITALIC
    );
    
    // Light theme
    public static final AnsiCode LIGHT_HEADING = new CompositeStyle(
        ColorCode.BLUE,
        StyleCode.BOLD
    );
    
    public static final AnsiCode LIGHT_BODY = new CompositeStyle(
        ColorCode.BLACK
    );
    
    public static final AnsiCode LIGHT_ACCENT = new CompositeStyle(
        ColorCode.CYAN,
        StyleCode.ITALIC
    );
    
    public static void registerDarkTheme() {
        Map<String, AnsiCode> theme = Map.of(
            "heading", DARK_HEADING,
            "body", DARK_BODY,
            "accent", DARK_ACCENT
        );
        Clique.registerStyle(theme);
    }
    
    public static void registerLightTheme() {
        Map<String, AnsiCode> theme = Map.of(
            "heading", LIGHT_HEADING,
            "body", LIGHT_BODY,
            "accent", LIGHT_ACCENT
        );
        Clique.registerStyle(theme);
    }
}

// Switch themes at runtime
ThemeStyles.registerDarkTheme();
Clique.parser().print("[heading]Dark Theme Title[/]");
```

### Semantic Composite Styles

Build semantic styles from base components:
```java
public class SemanticStyles {
    // Base components
    private static final AnsiCode ERROR_COLOR = ColorCode.BRIGHT_RED;
    private static final AnsiCode WARNING_COLOR = ColorCode.BRIGHT_YELLOW;
    private static final AnsiCode INFO_COLOR = ColorCode.BRIGHT_CYAN;
    private static final AnsiCode SUCCESS_COLOR = ColorCode.BRIGHT_GREEN;
    
    // Composed semantic styles
    public static final AnsiCode ERROR = new CompositeStyle(
        ERROR_COLOR,
        StyleCode.BOLD
    );
    
    public static final AnsiCode ERROR_CRITICAL = new CompositeStyle(
        ERROR_COLOR,
        StyleCode.BOLD,
        StyleCode.UNDERLINE,
        BackgroundCode.BRIGHT_BLACK
    );
    
    public static final AnsiCode WARNING = new CompositeStyle(
        WARNING_COLOR,
        StyleCode.BOLD
    );
    
    public static final AnsiCode INFO = new CompositeStyle(
        INFO_COLOR
    );
    
    public static final AnsiCode SUCCESS = new CompositeStyle(
        SUCCESS_COLOR,
        StyleCode.BOLD
    );
    
    public static void register() {
        Map<String, AnsiCode> styles = Map.of(
            "error", ERROR,
            "error-critical", ERROR_CRITICAL,
            "warning", WARNING,
            "info", INFO,
            "success", SUCCESS
        );
        Clique.registerStyle(styles);
    }
}

// Usage
SemanticStyles.register();
Clique.parser().print("[error]Error: File not found[/]");
Clique.parser().print("[error-critical]CRITICAL: System failure![/]");
Clique.parser().print("[success]Build completed successfully[/]");
```

## Integrating External Styling Systems

### Loading from Configuration Files

Create styles from external configuration:
```java
public class StyleLoader {
    public static void loadFromJson(String jsonPath) throws IOException {
        // Example JSON structure:
        // {
        //   "styles": {
        //     "primary": {"r": 66, "g": 135, "b": 245},
        //     "secondary": {"r": 156, "g": 39, "b": 176}
        //   }
        // }
        
        String json = Files.readString(Path.of(jsonPath));
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject styles = root.getAsJsonObject("styles");
        
        Map<String, AnsiCode> customStyles = new HashMap<>();
        
        for (Map.Entry<String, JsonElement> entry : styles.entrySet()) {
            JsonObject color = entry.getValue().getAsJsonObject();
            int r = color.get("r").getAsInt();
            int g = color.get("g").getAsInt();
            int b = color.get("b").getAsInt();
            
            customStyles.put(entry.getKey(), new RGBColor(r, g, b, false));
        }
        
        Clique.registerStyle(customStyles);
    }
}

// Load styles from config
StyleLoader.loadFromJson("config/colors.json");
Clique.parser().print("[primary]Styled from config![/]");
```

### Material Design Color System

Implement Material Design colors:
```java
public class MaterialColors {
    // Material Design color palette
    private static final Map<String, int[]> COLORS = Map.ofEntries(
        Map.entry("md-red-500", new int[]{244, 67, 54}),
        Map.entry("md-pink-500", new int[]{233, 30, 99}),
        Map.entry("md-purple-500", new int[]{156, 39, 176}),
        Map.entry("md-deep-purple-500", new int[]{103, 58, 183}),
        Map.entry("md-indigo-500", new int[]{63, 81, 181}),
        Map.entry("md-blue-500", new int[]{33, 150, 243}),
        Map.entry("md-light-blue-500", new int[]{3, 169, 244}),
        Map.entry("md-cyan-500", new int[]{0, 188, 212}),
        Map.entry("md-teal-500", new int[]{0, 150, 136}),
        Map.entry("md-green-500", new int[]{76, 175, 80}),
        Map.entry("md-light-green-500", new int[]{139, 195, 74}),
        Map.entry("md-lime-500", new int[]{205, 220, 57}),
        Map.entry("md-yellow-500", new int[]{255, 235, 59}),
        Map.entry("md-amber-500", new int[]{255, 193, 7}),
        Map.entry("md-orange-500", new int[]{255, 152, 0}),
        Map.entry("md-deep-orange-500", new int[]{255, 87, 34})
    );
    
    public static void register() {
        Map<String, AnsiCode> styles = new HashMap<>();
        
        for (Map.Entry<String, int[]> entry : COLORS.entrySet()) {
            int[] rgb = entry.getValue();
            styles.put(entry.getKey(), new RGBColor(rgb[0], rgb[1], rgb[2], false));
            styles.put("bg-" + entry.getKey(), new RGBColor(rgb[0], rgb[1], rgb[2], true));
        }
        
        Clique.registerStyle(styles);
    }
}

// Use Material Design colors
MaterialColors.register();
Clique.parser().print("[md-indigo-500]Material Indigo[/]");
Clique.parser().print("[md-teal-500]Material Teal[/]");
Clique.parser().print("[bg-md-amber-500, black]Material Amber BG[/]");
```

### Tailwind CSS Color Integration

Port Tailwind CSS colors to CLI:
```java
public class TailwindColors {
    // Tailwind color system
    private static final Map<String, int[]> SLATE = Map.of(
        "tw-slate-50", new int[]{248, 250, 252},
        "tw-slate-500", new int[]{100, 116, 139},
        "tw-slate-900", new int[]{15, 23, 42}
    );
    
    private static final Map<String, int[]> SKY = Map.of(
        "tw-sky-50", new int[]{240, 249, 255},
        "tw-sky-500", new int[]{14, 165, 233},
        "tw-sky-900", new int[]{12, 74, 110}
    );
    
    private static final Map<String, int[]> ROSE = Map.of(
        "tw-rose-50", new int[]{255, 241, 242},
        "tw-rose-500", new int[]{244, 63, 94},
        "tw-rose-900", new int[]{136, 19, 55}
    );
    
    public static void register() {
        Map<String, AnsiCode> styles = new HashMap<>();
        
        // Register all Tailwind colors
        registerColorFamily(styles, SLATE);
        registerColorFamily(styles, SKY);
        registerColorFamily(styles, ROSE);
        
        Clique.registerStyle(styles);
    }
    
    private static void registerColorFamily(Map<String, AnsiCode> styles, Map<String, int[]> family) {
        for (Map.Entry<String, int[]> entry : family.entrySet()) {
            int[] rgb = entry.getValue();
            styles.put(entry.getKey(), new RGBColor(rgb[0], rgb[1], rgb[2], false));
        }
    }
}

// Use Tailwind colors
TailwindColors.register();
Clique.parser().print("[tw-sky-500]Tailwind Sky[/]");
Clique.parser().print("[tw-rose-500]Tailwind Rose[/]");
```

## Advanced ANSI Effects

### Hyperlinks (OSC 8)

Add clickable hyperlinks to terminal output:
```java
public class HyperlinkCode implements AnsiCode {
    private final String url;
    
    public HyperlinkCode(String url) {
        this.url = url;
    }
    
    @Override
    public String getCode() {
        // OSC 8 ; ; URL ST
        return "\u001B]8;;" + url + "\u001B\\";
    }
    
    public static AnsiCode closeLink() {
        return () -> "\u001B]8;;\u001B\\";
    }
}

// Register hyperlink styles
Clique.registerStyle("link-github", new HyperlinkCode("https://github.com"));
Clique.registerStyle("link-docs", new HyperlinkCode("https://docs.example.com"));
Clique.registerStyle("end-link", HyperlinkCode.closeLink());

// Create clickable links (terminal support required)
Clique.parser().print("[link-github, blue, ul]GitHub[end-link][/]");
```

### Cursor Movement and Positioning

Create styles that manipulate cursor position:
```java
public class CursorCode implements AnsiCode {
    private final String sequence;
    
    private CursorCode(String sequence) {
        this.sequence = sequence;
    }
    
    public static CursorCode up(int lines) {
        return new CursorCode("\u001B[" + lines + "A");
    }
    
    public static CursorCode down(int lines) {
        return new CursorCode("\u001B[" + lines + "B");
    }
    
    public static CursorCode savePosition() {
        return new CursorCode("\u001B[s");
    }
    
    public static CursorCode restorePosition() {
        return new CursorCode("\u001B[u");
    }
    
    @Override
    public String getCode() {
        return sequence;
    }
}

// Register cursor manipulation
Clique.registerStyle("save-pos", CursorCode.savePosition());
Clique.registerStyle("restore-pos", CursorCode.restorePosition());
```

### Progress Bar Styles

Create animated progress bar effects:
```java
public class ProgressStyles {
    public static final AnsiCode PROGRESS_COMPLETE = new CompositeStyle(
        new RGBColor(0, 255, 0, true),  // Green background
        ColorCode.BLACK,
        StyleCode.BOLD
    );
    
    public static final AnsiCode PROGRESS_INCOMPLETE = new CompositeStyle(
        new RGBColor(64, 64, 64, true),  // Gray background
        ColorCode.BRIGHT_BLACK
    );
    
    public static final AnsiCode PROGRESS_TEXT = new CompositeStyle(
        ColorCode.BRIGHT_CYAN,
        StyleCode.BOLD
    );
    
    public static void register() {
        Map<String, AnsiCode> styles = Map.of(
            "progress-done", PROGRESS_COMPLETE,
            "progress-todo", PROGRESS_INCOMPLETE,
            "progress-text", PROGRESS_TEXT
        );
        Clique.registerStyle(styles);
    }
}

// Build progress bar
ProgressStyles.register();
int percent = 65;
String bar = "█".repeat(percent / 5) + "░".repeat(20 - percent / 5);
Clique.parser().print(
    "[progress-text]Progress:[/] [progress-done]" + bar.substring(0, percent/5) +
    "[/][progress-todo]" + bar.substring(percent/5) + "[/] " + percent + "%"
);
```

## Dynamic Style Generation

### Gradient Generator

Generate color gradients dynamically:
```java
public class GradientGenerator {
    public static void registerGradient(String name, int[] startRGB, int[] endRGB, int steps) {
        Map<String, AnsiCode> gradient = new HashMap<>();
        
        for (int i = 0; i < steps; i++) {
            double ratio = (double) i / (steps - 1);
            int r = (int) (startRGB[0] + ratio * (endRGB[0] - startRGB[0]));
            int g = (int) (startRGB[1] + ratio * (endRGB[1] - startRGB[1]));
            int b = (int) (startRGB[2] + ratio * (endRGB[2] - startRGB[2]));
            
            gradient.put(name + "-" + i, new RGBColor(r, g, b, false));
        }
        
        Clique.registerStyle(gradient);
    }
}

// Create a sunset gradient
GradientGenerator.registerGradient(
    "sunset",
    new int[]{255, 94, 77},   // Coral
    new int[]{138, 35, 135},  // Purple
    10
);

// Use gradient steps
Clique.parser().print("[sunset-0]████[/][sunset-2]████[/][sunset-4]████[/][sunset-6]████[/][sunset-9]████[/]");
```

### Temperature-Based Colors

Generate colors based on values:
```java
public class TemperatureColors {
    public static AnsiCode forTemperature(double temp) {
        // Cold (blue) -> Warm (yellow) -> Hot (red)
        if (temp < 0) {
            return new RGBColor(0, 100, 255, false);  // Blue
        } else if (temp < 20) {
            int green = (int) (temp * 12.75);
            return new RGBColor(0, green, 255, false);  // Blue to cyan
        } else if (temp < 40) {
            int red = (int) ((temp - 20) * 12.75);
            return new RGBColor(red, 255, 0, false);  // Cyan to yellow
        } else {
            return new RGBColor(255, 0, 0, false);  // Red
        }
    }
    
    public static void registerTemperatureScale() {
        Map<String, AnsiCode> temps = new HashMap<>();
        for (int i = -20; i <= 50; i += 5) {
            temps.put("temp-" + i, forTemperature(i));
        }
        Clique.registerStyle(temps);
    }
}

// Display temperature with color
TemperatureColors.registerTemperatureScale();
Clique.parser().print("[temp--10]-10°C[/] [temp-0]0°C[/] [temp-20]20°C[/] [temp-40]40°C[/]");
```

## Best Practices

### Encapsulate Custom Codes

Create reusable classes for your custom codes:
```java
public class AppStyles {
    private static boolean registered = false;
    
    public static synchronized void register() {
        if (registered) return;
        
        // Your registration logic
        Map<String, AnsiCode> styles = new HashMap<>();
        // ... add styles
        Clique.registerStyle(styles);
        
        registered = true;
    }
}

// Ensure styles are registered once
AppStyles.register();
```

### Terminal Capability Detection

Check terminal capabilities before registering advanced features:
```java
public class AdvancedStyles {
    public static void registerIfSupported() {
        if (supports256Colors()) {
            register256Colors();
        }
        
        if (supportsTrueColor()) {
            registerTrueColors();
        }
        
        if (supportsHyperlinks()) {
            registerHyperlinks();
        }
    }
    
    private static boolean supports256Colors() {
        String term = System.getenv("TERM");
        return term != null && term.contains("256color");
    }
    
    private static boolean supportsTrueColor() {
        String colorterm = System.getenv("COLORTERM");
        return colorterm != null && 
               (colorterm.equals("truecolor") || colorterm.equals("24bit"));
    }
}
```

### Fallback Styles

Provide fallbacks for terminals without advanced support:
```java
public class StyleWithFallback implements AnsiCode {
    private final AnsiCode primary;
    private final AnsiCode fallback;
    private final boolean useAdvanced;
    
    public StyleWithFallback(AnsiCode primary, AnsiCode fallback, boolean useAdvanced) {
        this.primary = primary;
        this.fallback = fallback;
        this.useAdvanced = useAdvanced;
    }
    
    @Override
    public String getCode() {
        return useAdvanced ? primary.getCode() : fallback.getCode();
    }
}

// Register with fallback
boolean hasTrueColor = detectTrueColorSupport();
Clique.registerStyle("brand-primary", new StyleWithFallback(
    new RGBColor(66, 135, 245, false),  // True color
    ColorCode.BRIGHT_BLUE,               // Fallback
    hasTrueColor
));
```

## See Also

- [Parser Documentation](parser.md) - Basic parser usage
- [Markup Reference](markup-reference.md) - Built-in styles
- [Tips and Tricks](tips.md) - General usage patterns