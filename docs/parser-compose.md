# Parser Composability

Extend Clique by creating custom ANSI codes, composing complex styles, and integrating external design systems.

## Understanding AnsiCode

All styles in Clique implement the `AnsiCode` interface:

```java
public interface AnsiCode {
    String getCode();
    String toString();
}
```

**Important:** You must implement **both** methods. `getCode()` returns the ANSI escape sequence, and `toString()` should return the same value for compatibility with Clique's parser.

## Creating Custom ANSI Codes

### RGB True Color Support

Add full 24-bit RGB colors to your terminal output:

```java
import com.github.kusoroadeolu.clique.ansi.AnsiCode;

public class RGBColor implements AnsiCode {
    private final String code;
    
    public RGBColor(int r, int g, int b, boolean isBackground) {
        r = clamp(r, 0, 255);
        g = clamp(g, 0, 255);
        b = clamp(b, 0, 255);
        
        int type = isBackground ? 48 : 38;
        this.code = String.format("\u001B[%d;2;%d;%d;%dm", type, r, g, b);
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String toString() {
        return code;
    }
    
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}

// Register and use
Clique.registerStyle("coral", new RGBColor(255, 127, 80, false));
Clique.parser().print("[coral]Beautiful coral text![/]");
```

**Pro tip:** Pre-compute the ANSI code in the constructor for better performance.

### 256-Color Support

For terminals with 256-color support:

```java
public class Extended256Color implements AnsiCode {
    private final String code;
    
    public Extended256Color(int colorNumber, boolean isBackground) {
        if (colorNumber < 0 || colorNumber > 255) {
            throw new IllegalArgumentException("Color must be 0-255");
        }
        int type = isBackground ? 48 : 38;
        this.code = String.format("\u001B[%d;5;%dm", type, colorNumber);
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String toString() {
        return code;
    }
}

// Register 256 colors
Clique.registerStyle("orange", new Extended256Color(214, false));
Clique.parser().print("[orange]Orange text![/]");
```

### Basic Custom Effects

Create any ANSI effect:

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
    
    @Override
    public String toString() {
        return code;
    }
}

// Register custom effects
Clique.registerStyle("blink", new CustomAnsiCode("\u001B[5m"));
Clique.parser().print("[blink]Blinking text![/]");
```

## Composing Complex Styles
Combine multiple ANSI codes into reusable semantic styles:

```java
import com.github.kusoroadeolu.clique.ansi.ColorCode;
import com.github.kusoroadeolu.clique.ansi.StyleCode;

public class CompositeStyle implements AnsiCode {
    private final String compositeCode;
    
    public CompositeStyle(AnsiCode... codes) {
        StringBuilder sb = new StringBuilder();
        for (AnsiCode code : codes) {
            sb.append(code.getCode());
        }
        this.compositeCode = sb.toString();
    }
    
    @Override
    public String getCode() {
        return compositeCode;
    }
    
    @Override
    public String toString() {
        return compositeCode;
    }
}

// Create semantic styles
AnsiCode errorStyle = new CompositeStyle(
    ColorCode.BRIGHT_RED,
    StyleCode.BOLD,
    StyleCode.UNDERLINE
);

AnsiCode successStyle = new CompositeStyle(
    ColorCode.BRIGHT_GREEN,
    StyleCode.BOLD
);

// Register them
Clique.registerStyle("error", errorStyle);
Clique.registerStyle("success", successStyle);

// Use with a single tag
Clique.parser().print("[error]Critical error![/]");
Clique.parser().print("[success]Operation complete![/]");
```

## Example: Design System Integration

Here's a complete example integrating the Catppuccin Mocha color palette:
```java
import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.ansi.StyleCode;
import java.util.HashMap;
import java.util.Map;

public class CatppuccinColors {
    
    // RGBColor implementation
    public static class RGBColor implements AnsiCode {
        private final String code;
        
        public RGBColor(int r, int g, int b, boolean isBackground) {
            r = clamp(r, 0, 255);
            g = clamp(g, 0, 255);
            b = clamp(b, 0, 255);
            int type = isBackground ? 48 : 38;
            this.code = String.format("\u001B[%d;2;%d;%d;%dm", type, r, g, b);
        }
        
        @Override
        public String getCode() { return code; }
        
        @Override
        public String toString() { return code; }
        
        private int clamp(int v, int min, int max) {
            return Math.max(min, Math.min(max, v));
        }
    }
    
    // CompositeStyle implementation
    private static class CompositeStyle implements AnsiCode {
        private final String compositeCode;
        
        public CompositeStyle(AnsiCode... codes) {
            StringBuilder sb = new StringBuilder();
            for (AnsiCode code : codes) {
                sb.append(code.getCode());
            }
            this.compositeCode = sb.toString();
        }
        
        @Override
        public String getCode() { return compositeCode; }
        
        @Override
        public String toString() { return compositeCode; }
    }
    
    // Catppuccin Mocha palette
    private static final Map<String, int[]> PALETTE = new HashMap<>() {{
        put("ctp-mauve", new int[]{203, 166, 247});
        put("ctp-pink", new int[]{245, 194, 231});
        put("ctp-red", new int[]{243, 139, 168});
        put("ctp-peach", new int[]{250, 179, 135});
        put("ctp-yellow", new int[]{249, 226, 175});
        put("ctp-green", new int[]{166, 227, 161});
        put("ctp-blue", new int[]{137, 180, 250});
        put("ctp-text", new int[]{205, 214, 244});
    }};
    
    public static void register() {
        Map<String, AnsiCode> styles = new HashMap<>();
        
        // Register all colors 
        for (Map.Entry<String, int[]> entry : PALETTE.entrySet()) {
            int[] rgb = entry.getValue();
            styles.put(entry.getKey(), new RGBColor(rgb[0], rgb[1], rgb[2], false));
            styles.put("bg-" + entry.getKey(), new RGBColor(rgb[0], rgb[1], rgb[2], true));
        }
        
        Clique.registerStyle(styles);
    }
    
    public static void registerSemanticStyles() {
        Map<String, AnsiCode> semantic = new HashMap<>();
        
        // Create semantic aliases
        semantic.put("success", getColor("ctp-green"));
        semantic.put("error", getColor("ctp-red"));
        semantic.put("warning", getColor("ctp-yellow"));
        
        // Create composite styles
        semantic.put("heading", new CompositeStyle(
            getColor("ctp-mauve"),
            StyleCode.BOLD,
            StyleCode.UNDERLINE
        ));
        
        Clique.registerStyle(semantic);
    }
    
    private static AnsiCode getColor(String name) {
        int[] rgb = PALETTE.get(name.replace("bg-", ""));
        boolean isBg = name.startsWith("bg-");
        return new RGBColor(rgb[0], rgb[1], rgb[2], isBg);
    }
}

// Usage
CatppuccinColors.register();
CatppuccinColors.registerSemanticStyles();

Clique.parser().print("[ctp-mauve]Catppuccin Mauve![/]");
Clique.parser().print("[success]✓ Build successful[/]");
Clique.parser().print("[heading]My Heading[/]");
```

## Some Advanced Patterns
### Dynamic Style Generation

Generate gradients dynamically:

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
    5
);

// Use gradient
Clique.parser().print("[sunset-0]█[/][sunset-1]█[/][sunset-2]█[/][sunset-3]█[/][sunset-4]█[/]");
```

### Terminal Capability Detection

Provide fallbacks for terminals without RGB support:

```java
public class StyleWithFallback implements AnsiCode {
    private final String code;
    
    public StyleWithFallback(AnsiCode primary, AnsiCode fallback) {
        boolean hasTrueColor = supportsTrueColor();
        this.code = hasTrueColor ? primary.getCode() : fallback.getCode();
    }
    
    @Override
    public String getCode() { return code; }
    
    @Override
    public String toString() { return code; }
    
    private static boolean supportsTrueColor() {
        String colorterm = System.getenv("COLORTERM");
        return colorterm != null && 
               (colorterm.equals("truecolor") || colorterm.equals("24bit"));
    }
}

// Use with fallback
Clique.registerStyle("brand-primary", new StyleWithFallback(
    new RGBColor(66, 135, 245, false),  // True color
    ColorCode.BRIGHT_BLUE                // Fallback
));
```

### Loading from Configuration

Load colors from JSON:

```java
import com.google.gson.*;
import java.nio.file.*;

public class StyleLoader {
    public static void loadFromJson(String jsonPath) throws IOException {
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

// colors.json:
// {
//   "styles": {
//     "primary": {"r": 66, "g": 135, "b": 245},
//     "accent": {"r": 156, "g": 39, "b": 176}
//   }
// }

StyleLoader.loadFromJson("config/colors.json");
Clique.parser().print("[primary]Styled from config![/]");
```

## Best Practices

### 1. Pre-compute ANSI codes
Store the computed code in the constructor rather than generating it every time `getCode()` is called.

```java
// Good
public class RGBColor implements AnsiCode {
    private final String code;
    
    public RGBColor(int r, int g, int b, boolean isBackground) {
        // Compute once
        this.code = String.format("...", ...);
    }
}

// Bad
public class RGBColor implements AnsiCode {
    @Override
    public String getCode() {
        // Computes every time
        return String.format("...", ...);
    }
}
```

### 2. Always implement both interface methods
The parser requires both `getCode()` and `toString()` to work correctly.

```java
@Override
public String getCode() {
    return code;
}

@Override
public String toString() {
    return code;  // Return the same value
}
```

### 3. Encapsulate registration logic
Create a single registration point for your custom styles:

```java
public class AppStyles {
    private static boolean registered = false;
    
    public static synchronized void register() {
        if (registered) return;
        
        Map<String, AnsiCode> styles = new HashMap<>();
        // ... add all your styles
        Clique.registerStyle(styles);
        
        registered = true;
    }
}

// Call once at startup
AppStyles.register();
```


## Common Pitfalls

### Missing toString() implementation
If you only implement `getCode()`, the parser will output the object's default `toString()` instead of the ANSI codes.

**Symptom:** You see `com.example.RGBColor@abc123` instead of colored text.

**Fix:** Implement `toString()` to return the same value as `getCode()`.

### Forgetting to register styles
Custom styles must be registered before use.

```java
// Create the style
AnsiCode myColor = new RGBColor(255, 0, 0, false);

//  This won't work - style not registered
Clique.parser().print("[mycolor]Text[/]");

// Register first
Clique.registerStyle("mycolor", myColor);
Clique.parser().print("[mycolor]Text[/]");
```

## See Also

- [Parser Documentation](parser.md) - Basic parser usage
- [Markup Reference](markup-reference.md) - Built-in styles
- [Tips and Tricks](tips.md) - General usage patterns