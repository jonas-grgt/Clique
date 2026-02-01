# Progress Bars

Progress bars provide visual feedback for long-running operations. They're perfect for file processing, downloads, builds, or any task where you want to show completion status with elapsed and remaining time estimates.

## Basic Usage

### Simple Progress Bar
```java
ProgressBar bar = Clique.progressBar(100);

for (int i = 0; i < 100; i++) {
    bar.tick();
    bar.render();
    Thread.sleep(50);
}
```

### With Configuration
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(50)
    .complete('=')
    .incomplete('-')
    .build();

ProgressBar bar = Clique.progressBar(100, config);

while (!bar.isDone()) {
    bar.tick();
    bar.render();
    Thread.sleep(100);
}
```

## Progress Bar Methods

### Increment Progress

Move the progress bar forward:
```java
bar.tick();        // Increment by 1
bar.tick(5);       // Increment by 5
```

### Complete Instantly
```java
bar.complete();    // Jump to 100%
```

### Check Status
```java
if (bar.isDone()) {
    System.out.println("Finished!");
}
```

### Get Without Rendering
```java
String barString = bar.get();
System.out.println(barString);
```

## Format Tokens

Progress bars support several tokens that get replaced with actual values:

- **`:bar`** - The progress bar itself
- **`:percent`** - Current percentage (0-100)
- **`:progress`** - Current tick count
- **`:total`** - Total tick count
- **`:elapsed`** - Time elapsed (mm:ss)
- **`:remaining`** - Estimated time remaining (mm:ss)

### Using Format Tokens
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format(":bar :percent% [:progress/:total] [:elapsed/:remaining]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

## Progress Bar Configuration

Use `ProgressBarConfiguration` to customize appearance and behavior.

**Note:** Markup parsing is enabled by default.

### Basic Configuration
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(40)
    .complete('█')
    .incomplete('░')
    .format(":bar :percent%")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Configuration Options

#### Bar Length

Set the width of the progress bar in characters:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(60)  // 60 characters wide
    .build();
```

#### Complete/Incomplete Characters

Customize the characters used for completed and incomplete portions:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .complete('█')    // Filled portion
    .incomplete('░')  // Empty portion
    .build();
```

**Common character sets:**
- Block: `█` and `░`
- Equals: `=` and `-`
- Hash: `#` and `.`
- Arrow: `>` and `-`
Though only the blocks are provided by default

#### Custom Format

Define your own format string using tokens:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("[cyan]:bar[/] :percent% | :progress/:total | ETA: :remaining")
    .build();
```

#### Custom Parser

Provide a custom configured parser for markup processing:
```java
ParserConfiguration parserConfig = ParserConfiguration
    .immutableBuilder()
    .delimiter(' ')
    .build();

ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .parser(Clique.parser().configuration(parserConfig))
    .build();
```

## Conditional Styling

Progress bars support dynamic styling based on completion percentage. This is perfect for showing different states as progress advances.

### Style Predicates

Use `styleWhen()` to apply formats based on custom conditions:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleWhen(p -> p < 25, "[red, bold]:bar[/] [red]:percent% - SLOW![/]")
    .styleWhen(p -> p >= 25 && p < 75, "[blue]:bar[/] :percent%")
    .styleWhen(p -> p >= 75 && p < 100, "[green]:bar[/] :percent%")
    .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]COMPLETE![/]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

**How it works:**
- The first matching predicate wins
- If no predicates match, falls back to the default format
- Predicates are checked in the order they're added

### Style Ranges

Use `styleRange()` as a shorthand for range based conditions:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleRange(0, 30, "[yellow]:bar[/] [yellow]Starting...[/]")
    .styleRange(30, 70, "[cyan]:bar[/] [cyan]Processing...[/]")
    .styleRange(70, 100, "[green]:bar[/] [green]Almost done![/]")
    .build();
```

**Note:** `styleRange(min, max, format)` is equivalent to `styleWhen(p -> p >= min && p < max, format)`

### Multiple Conditions Example
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(40)
    // Special state for completion
    .styleWhen(p -> p == 100, "[green, bold]:bar[/] ✓ Done! [:elapsed][/]")
    // Warning for slow progress
    .styleWhen(p -> p < 10, "[red]:bar[/] [red]:percent% - Initializing...[/]")
    // Normal operation
    .styleWhen(p -> p >= 10, "[cyan]:bar[/] :percent% [:elapsed/:remaining]")
    .build();
```

## Using Markup in Progress Bars

Progress bars automatically parse markup tags in format strings:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("[bold, cyan]:bar[/] [yellow]:percent%[/] [:elapsed/:remaining]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

## Examples

### File Processing
```java
List<String> files = List.of(
    "report.pdf", "invoice.xlsx", "backup.zip",
    "presentation.pptx", "database.sql"
);

ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(50)
    .styleWhen(p -> p < 30, "[yellow]:bar[/] [yellow]:percent%[/] - Starting...")
    .styleWhen(p -> p >= 30 && p < 70, "[cyan]:bar[/] [cyan]:percent%[/] [:elapsed]")
    .styleWhen(p -> p >= 70 && p < 100, "[green]:bar[/] [green]:percent%[/] - Almost done!")
    .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]✓ Complete![/] [:elapsed]")
    .build();

ProgressBar bar = Clique.progressBar(files.size(), config);

for (String file : files) {
    // Process file...
    Thread.sleep(500);
    
    bar.tick();
    bar.render();
}
```

### Download Progress
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(40)
    .complete('█')
    .incomplete('░')
    .format("[cyan]:bar[/] :percent% | :progress/:total MB | ETA: :remaining")
    .build();

int totalMB = 2800;
ProgressBar bar = Clique.progressBar(totalMB, config);

while (!bar.isDone()) {
    // Simulate download chunks (5-15 MB)
    int chunk = 5 + (int)(Math.random() * 10);
    bar.tick(chunk);
    bar.render();
    Thread.sleep(100);
}

System.out.println("\nDownload complete!");
```

### Build System Stages
```java
public static void runStage(String name, int steps, String color) throws InterruptedException {
    Clique.parser().print(color + "► " + name + "[/]");
    
    ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
        .length(30)
        .format("  " + color + ":bar[/] :percent%")
        .build();
    
    ProgressBar bar = Clique.progressBar(steps, config);
    
    while (!bar.isDone()) {
        Thread.sleep(50);
        bar.tick();
        bar.render();
    }
    
}

// Usage
runStage("Compiling sources", 45, "[blue]");
runStage("Running tests", 30, "[yellow]");
runStage("Packaging", 15, "[magenta]");
runStage("Deploying", 10, "[green]");
```

### Multi-State Progress
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(50)
    // Critical state
    .styleWhen(p -> p < 20, "[red, bold]:bar[/] [red]:percent% - CRITICAL[/]")
    // Warning state
    .styleRange(20, 50, "[yellow]:bar[/] [yellow]:percent% - Warning[/]")
    // Normal state
    .styleRange(50, 80, "[blue]:bar[/] :percent%")
    // Success state
    .styleRange(80, 100, "[green]:bar[/] [green]:percent% - Good![/]")
    // Complete
    .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]✓ COMPLETE![/]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Simple Loading Bar
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(30)
    .format("Loading :bar :percent%")
    .build();

ProgressBar bar = Clique.progressBar(100, config);

while(!bar.isDone()) {
    bar.tick();
    bar.render();
    Thread.sleep(50);
}
```

## Rendering Behavior

### Default Rendering

The `render()` method prints to `System.out`:
```java
bar.render();  // Prints to stdout
```

### Custom Output Stream

You can render to a different stream:
```java
bar.render(System.err);  // Prints to stderr
```

### Carriage Return Behavior

Progress bars use carriage return (`\r`) to update in place:
- While incomplete: Updates on the same line
- When complete: Adds a newline automatically

## Things to Watch Out For

- **Fast Updates**: If you're ticking thousands of times per second, consider throttling renders to avoid terminal flickering
- **Total of Zero**: If total is 0, the bar shows 0% and handles gracefully
- **Negative Ticks**: Negative tick amounts are clamped to 0
- **Over-ticking**: Ticking past total is capped at 100%
- **Time Estimates**: Remaining time shows `--:--` until at least one tick has occurred

## See Also

- [Markup Reference](markup-reference.md) - Styling options for progress bar formats
- [Parser Documentation](parser.md) - How markup parsing works