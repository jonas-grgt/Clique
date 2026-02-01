# Progress Bars

Progress bars provide visual feedback for long-running operations. They display completion percentage, elapsed time, estimated remaining time, and support dynamic styling based on progress.

## Basic Usage

### Simple Progress Bar
```java
ProgressBar bar = Clique.progressBar(100);

for (int i = 0; i < 100; i++) {
    bar.tick();
    bar.render();
    Thread.sleep(100);
}
```

This creates a default progress bar that counts from 0 to 100.

### With Custom Increments
```java
ProgressBar bar = Clique.progressBar(1000);

// Tick by larger amounts
bar.tick(50);
bar.render();

bar.tick(100);
bar.render();
```

## Progress Bar Methods

### Tick

Increment the progress bar:
```java
bar.tick();      // Increment by 1
bar.tick(10);    // Increment by 10
```

The progress bar automatically caps at the total value and won't go negative.

### Complete

Jump directly to 100% completion:
```java
bar.complete();
bar.render();
```

### Get Output

Get the formatted progress bar string without rendering:
```java
String output = bar.get();
System.out.println(output);
```

### Render

Display the progress bar in the terminal:
```java
bar.render();  // Prints to System.out with carriage return
```

The progress bar uses `\r` to update in place. When complete, it prints a newline.

## Progress Bar Configuration

Customize appearance and behavior using `ProgressBarConfiguration`.

**Note:** Markup parsing is enabled by default.

### Basic Configuration
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(50)
    .complete('=')
    .incomplete('-')
    .format(":bar :percent%")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Configuration Options

#### Bar Length

Set the width of the progress bar in characters:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .length(60)  // 60 character wide bar (default is 40)
    .build();
```

#### Complete and Incomplete Characters

Customize the characters used for filled and unfilled portions:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .complete('█')  // Default
    .incomplete('░')  // Default
    .build();
```

**Examples:**
```java
// ASCII style
.complete('=')
.incomplete('-')

// Block style
.complete('▓')
.incomplete('▒')

// Arrow style
.complete('>')
.incomplete('.')
```

#### Format String

Define what information to display and how:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format(":bar :percent% [:elapsed/:remaining]")
    .build();
```

**Available tokens:**
- `:bar` - The progress bar itself
- `:percent` - Current percentage (0-100)
- `:progress` - Current tick count
- `:total` - Total tick count
- `:elapsed` - Time elapsed (mm:ss format)
- `:remaining` - Estimated time remaining (mm:ss format)

**Format examples:**
```java
// Minimal
.format(":bar :percent%")

// Detailed
.format(":bar :progress/:total (:percent%) :elapsed/:remaining")

// Custom
.format("Progress: :bar | :percent% complete")
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

## Dynamic Styling

Progress bars can change their appearance based on completion percentage.

### Style Ranges

Apply different styles to different percentage ranges:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleRange(0, 30, "[red]:bar[/] [red, bold]:percent%[/] :elapsed/:remaining")
    .styleRange(30, 70, "[yellow]:bar[/] [yellow, bold]:percent%[/] :elapsed/:remaining")
    .styleRange(70, 100, "[green]:bar[/] [green, bold]:percent%[/] :elapsed/:remaining")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

The bar will be:
- **Red** from 0-29%
- **Yellow** from 30-69%
- **Green** from 70-99%

Ranges are inclusive of the minimum and exclusive of the maximum (`min <= percent < max`).

### Custom Predicates

Use predicates for more complex conditional styling:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleWhen(p -> p < 25, "[red, bold]:bar[/] [red]:percent% - SLOW![/]")
    .styleWhen(p -> p >= 25 && p < 75, "[blue]:bar[/] :percent%")
    .styleWhen(p -> p >= 75 && p < 100, "[green]:bar[/] :percent%")
    .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]COMPLETE![/]")
    .build();
```

**Important:** The first matching predicate wins. Order matters!

```java
// This example shows priority
.styleWhen(p -> p >= 50, "FIRST")   // Matches at 50%
.styleWhen(p -> p >= 50, "SECOND")  // Never matches (FIRST already won)
```

### Fallback Behavior

If no style matches, the bar uses the default format:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("Default: :bar :percent%")  // Used when no styles match
    .styleWhen(p -> p == 100, "[green, bold]DONE![/]")
    .build();
```

## Using Markup in Progress Bars

Progress bars automatically parse markup tags in format strings:
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("[cyan]:bar[/] [yellow, bold]:percent%[/] [:elapsed/:remaining]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

You can style individual tokens differently:
```java
.format("[blue, bold]:bar[/] [green]:percent%[/] [dim]:elapsed/:remaining[/]")
```

## Examples

### Download Progress
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("Downloading: :bar :percent% [:elapsed/:remaining]")
    .complete('█')
    .incomplete('░')
    .length(40)
    .build();

ProgressBar bar = Clique.progressBar(totalBytes, config);

while (downloading) {
    int bytesRead = readChunk();
    bar.tick(bytesRead);
    bar.render();
}
```

### Build Progress with Status Colors
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleRange(0, 30, "[red]:bar[/] [red]:percent%[/] Building...")
    .styleRange(30, 60, "[yellow]:bar[/] [yellow]:percent%[/] Testing...")
    .styleRange(60, 90, "[blue]:bar[/] [blue]:percent%[/] Packaging...")
    .styleRange(90, 100, "[green]:bar[/] [green]:percent%[/] Almost done!")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Loading with Custom Characters
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .complete('▓')
    .incomplete('▒')
    .format("[cyan]▕:bar▏[/] :percent%")
    .length(30)
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### Minimal Progress Indicator
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format(":progress/:total")
    .build();

ProgressBar bar = Clique.progressBar(1000, config);
// Output: "  45/1000"
```

### Processing with State Messages
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleWhen(p -> p < 25, "[yellow]:bar[/] Initializing...")
    .styleWhen(p -> p >= 25 && p < 50, "[blue]:bar[/] Processing data...")
    .styleWhen(p -> p >= 50 && p < 75, "[blue]:bar[/] Analyzing results...")
    .styleWhen(p -> p >= 75 && p < 100, "[cyan]:bar[/] Finalizing...")
    .styleWhen(p -> p == 100, "[green, bold]:bar[/] Complete! ✓[/]")
    .build();

ProgressBar bar = Clique.progressBar(100, config);
```

### ASCII Art Style
```java
ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .complete('>')
    .incomplete('.')
    .format("[:bar] :percent%")
    .length(50)
    .build();

// Output: [>>>>>>>>>>>>>........................] 25%
```

## Time Display

### Elapsed Time

Shows time since the progress bar was created:
```java
.format(":bar :elapsed")
    
// Output: "████░░░░░░ 00:15"
```

### Remaining Time

Estimates time remaining based on current progress:
```java
.format(":bar :remaining")
    
// Output: "████░░░░░░ 00:45"
```

**Note:** Remaining time shows `--:--` when no progress has been made yet (can't estimate).

### Combined Time Display
```java
.format(":bar [:elapsed/:remaining]")
    
// Output: "████░░░░░░ [00:15/00:45]"
```

## Things to Watch Out For

- Progress bars use `\r` (carriage return) to update in place. Make sure not to print other content while a progress bar is active.
- When the bar reaches 100%, it automatically prints a newline to prevent overlap with future output.
- The `tick()` method automatically prevents going over the total or below zero.
- Style predicates are evaluated in order - the first match wins.
- Time estimates are only accurate after some progress has been made.

## See Also

- [Markup Reference](markup-reference.md) - Styling options for progress bar content
- [Parser Documentation](parser.md) - How markup parsing works