# Progress Bars

Progress bars provide visual feedback for long-running operations. Clique includes predefined styles and extensive customization options.

## Quick Start

### Basic Progress Bar

```java
ProgressBar bar = Clique.progressBar(100);

while (!bar.isDone()) {
    bar.tick();
    bar.render();
    Thread.sleep(50);
}
```

### Using Predefined Presets

Clique provides several built-in presets:

```java
// Blocks style (default)
ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.BLOCKS);

// Lines style
ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.LINES);

// Bold style
ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.BOLD);

// Classic style
ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.CLASSIC);

// Dots style
ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.DOTS);
```

## Predefined Styles

### BLOCKS (Default)
```
████████████████████████████████░░░░░  80% [00:12/00:03]
```
- Complete: `█`
- Incomplete: `░`
- Length: 40
- Format: `:bar :percent% [:elapsed/:remaining]`

### LINES
```
▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▁▁▁▁▁▁▁▁▁▁ 80%
```
- Complete: `▂`
- Incomplete: `▁`
- Length: 50
- Format: `:bar :percent%`

### BOLD
```
▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▰▱▱▱▱▱▱▱▱ 80% | 80/100
```
- Complete: `▰`
- Incomplete: `▱`
- Length: 40
- Format: `:bar :percent% | :progress/:total`

### CLASSIC
```
[##############################====================] 60% [00:45]
```
- Complete: `#`
- Incomplete: `=`
- Length: 50
- Format: `[:bar] :percent% [:elapsed]`

### DOTS
```
●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●○○○○○○○○○○○○○○○○○○○○○ 58%
```
- Complete: `●`
- Incomplete: `○`
- Length: 50
- Format: `:bar :percent%`

## Custom Configuration

Build your own progress bar style using `ProgressBarConfiguration`:

```java
ProgressBarConfiguration config = ProgressBarConfiguration.builder()
    .length(60)
    .complete('▓')
    .incomplete('░')
    .format("[cyan]:bar[/] :percent% | :progress/:total")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Configuration Options

#### Length
Width of the progress bar in characters:
```java
.length(50)
```

#### Complete/Incomplete Characters
Characters used for filled and unfilled portions:
```java
.complete('█')
.incomplete('░')
```

#### Format String
Template for the progress bar output with placeholders:
```java
.format(":bar :percent% [:elapsed/:remaining]")
```

**Available placeholders:**
- `:bar` - The actual progress bar visualization
- `:percent` - Current percentage (0-100)
- `:progress` - Current tick count
- `:total` - Total tick count
- `:elapsed` - Elapsed time (MM:SS)
- `:remaining` - Estimated remaining time (MM:SS)

#### Custom Parser
Use a custom parser for markup processing:
```java
.parser(Clique.parser())
```

## Dynamic Styling

Change the format based on progress percentage:

```java
ProgressBarConfiguration config = ProgressBarConfiguration.builder()
    .styleRange(0, 30, "[red]:bar[/] :percent% [red]Starting...[/]")
    .styleRange(30, 70, "[yellow]:bar[/] :percent% [yellow]In Progress...[/]")
    .styleRange(70, 100, "[green]:bar[/] :percent% [green]Almost Done![/]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Custom Conditions

Use `styleWhen()` for custom conditions:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.builder()
    .styleWhen(p -> p < 50, "[red]:bar[/] :percent%")
    .styleWhen(p -> p >= 50 && p < 90, "[yellow]:bar[/] :percent%")
    .styleWhen(p -> p >= 90, "[green]:bar[/] :percent%")
    .build();
```

## Progress Bar Methods

### tick()
Increment progress by 1:
```java
bar.tick();
```

### tick(amount)
Increment progress by a specific amount:
```java
bar.tick(10);
```

### tickAnimated(amount)
Increment with smooth easing animation (requires easing configuration):
```java
bar.tickAnimated(25);
```

### complete()
Jump to 100%:
```java
bar.complete();
```

### isDone()
Check if progress is complete:
```java
if (bar.isDone()) {
    System.out.println("Finished!");
}
```

### render()
Display the progress bar:
```java
bar.render();  // Prints to System.out
```

### get()
Get the formatted progress bar string without rendering:
```java
String barText = bar.get();
System.out.println(barText);
```

## Quick Examples

### File Processing
```java
ProgressBarConfiguration config = ProgressBarConfiguration.builder()
    .format("[blue]:bar[/] :progress/:total files [:elapsed/:remaining]")
    .build();

ProgressBar bar = Clique.progressBar(files.size(), config);

for (File file : files) {
    processFile(file);
    bar.tick();
    bar.render();
}
```

### Downloading a File
```java
ProgressBarConfiguration config = ProgressBarConfiguration.builder()
    .length(50)
    .complete('▓')
    .incomplete('░')
    .format("[cyan]↓[/] :bar :percent% | :progress/:total MB")
    .styleRange(0, 100, "[cyan]:bar[/] :percent% | :progress/:total MB")
    .build();

ProgressBar bar = Clique.progressBar(totalMB, config);

while (downloading) {
    int downloaded = getDownloadedMB();
    bar.tick(downloaded);
    bar.render();
    Thread.sleep(100);
}
```

### Batch Processing with Status
```java
ProgressBarConfiguration config = ProgressBarConfiguration.builder()
    .styleRange(0, 50, "[red]:bar[/] :percent% [dim]Processing...[/]")
    .styleRange(50, 90, "[yellow]:bar[/] :percent% [dim]Finalizing...[/]")
    .styleRange(90, 100, "[green]:bar[/] :percent% [bold]Complete![/]")
    .build();

ProgressBar bar = Clique.progressBar(1000, config);

for (int i = 0; i < 1000; i++) {
    processBatch(i);
    bar.tick();
    if (i % 10 == 0) bar.render();
}
bar.complete();
```

## See Also

- [Progress Bar Easing](progress-bars-easing.md) - Smooth animations for progress updates
- [Markup Reference](markup-reference.md) - Styling options for progress bars