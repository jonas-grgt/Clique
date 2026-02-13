# Progress Bar Easing & Animation

Progress bars in Clique support smooth easing animations for tick updates. Instead of jumping instantly to new values, you can animate transitions with customizable easing functions.

## Why Use Easing?

Easing makes progress bars feel more polished and natural, especially when:
- Processing large batches where progress jumps significantly
- You want to draw attention to progress updates
- Creating a more refined user experience

For small, incremental updates (like `tick(1)` in a loop), instant jumps work fine and avoid unnecessary animation overhead.

## Basic Usage

### Instant Ticks (Default)

By default, progress bars update instantly:

```java
ProgressBar bar = Clique.progressBar(100);
bar.tick(50);  // Jumps instantly to 50%
bar.render();
```

### Animated Ticks

Use `tickAnimated()` to smoothly transition between values:

```java
EasingConfiguration easing = EasingConfiguration.immutableBuilder()
    .function(EasingFunction.EASE_OUT_CUBIC)
    .duration(500)
    .frames(30)
    .threshold(10)
    .build();

ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .easing(easing)
    .build();

ProgressBar bar = Clique.progressBar(100, config);

// Smooth 500ms animation, renders each frame automatically
bar.tickAnimated(50);

// Small ticks below threshold skip animation
bar.tickAnimated(2);  // Instant jump (below threshold of 10)
```

## Easing Configuration

Configure easing behavior with `EasingConfiguration`:

```java
EasingConfiguration easing = EasingConfiguration.immutableBuilder()
    .function(EasingFunction.EASE_IN_OUT_SINE)  // Easing curve
    .duration(1000)                              // Total animation time (ms)
    .frames(60)                                  // Number of frames
    .threshold(5)                                // Minimum tick amount to animate
    .build();
```

### Configuration Options

#### Easing Function

The mathematical curve that controls animation speed:

```java
.function(EasingFunction.EASE_OUT_CUBIC)
```

See [Available Easing Functions](#available-easing-functions) below for all options.

#### Duration

Total time the animation takes in milliseconds:

```java
.duration(500)  // 500ms animation
```

Longer durations = slower, more noticeable animations.  
Shorter durations = snappier, quicker animations.

#### Frames

Number of intermediate steps in the animation:

```java
.frames(30)  // 30 frames over the duration
```

More frames = smoother but more CPU intensive.  
Fewer frames = choppier but more performant.

The frame delay is calculated automatically: `duration / frames`

#### Threshold

Minimum tick amount required to trigger animation:

```java
.threshold(10)  // Only animate ticks >= 10
```

Ticks smaller than the threshold skip animation and jump instantly. This prevents unnecessary overhead for small incremental updates.

**Example:**
```java
EasingConfiguration easing = EasingConfiguration.immutableBuilder()
    .threshold(10)
    .build();

bar.tickAnimated(15);  // Animates (15 >= 10)
bar.tickAnimated(5);   // Instant jump (5 < 10)
```

## Available Easing Functions

Clique provides several easing functions based on [easings.net](https://easings.net):

### Linear

No easing - constant speed throughout:

```java
EasingFunction.LINEAR
```

Use when you want smooth animation without acceleration/deceleration.

### Sine Easing

Gentle, smooth curves:

```java
EasingFunction.EASE_IN_SINE       // Slow start, accelerates
EasingFunction.EASE_OUT_SINE      // Fast start, decelerates
EasingFunction.EASE_IN_OUT_SINE   // Slow start and end, fast middle
```

Best for subtle, natural-feeling animations.

### Quadratic Easing

More pronounced curves than sine:

```java
EasingFunction.EASE_IN_QUAD       // Accelerating from zero
EasingFunction.EASE_OUT_QUAD      // Decelerating to zero
EasingFunction.EASE_IN_OUT_QUAD   // Acceleration then deceleration
```

Good balance between subtlety and impact.

### Cubic Easing

Strong, dramatic curves:

```java
EasingFunction.EASE_IN_CUBIC      // Strong acceleration
EasingFunction.EASE_OUT_CUBIC     // Strong deceleration
EasingFunction.EASE_IN_OUT_CUBIC  // Strong start and end
```

Use for eye-catching, dramatic animations.

### Easing Function Comparison

| Function | Feel | Best For |
|----------|------|----------|
| `LINEAR` | Constant speed | Simple, predictable motion |
| `EASE_IN_SINE` | Gentle acceleration | Starting motion smoothly |
| `EASE_OUT_SINE` | Gentle deceleration | Ending motion smoothly |
| `EASE_IN_OUT_SINE` | Gentle both ends | Natural, organic movement |
| `EASE_IN_QUAD` | Moderate acceleration | Building momentum |
| `EASE_OUT_QUAD` | Moderate deceleration | Settling into place |
| `EASE_IN_OUT_QUAD` | Balanced curve | General purpose animation |
| `EASE_IN_CUBIC` | Strong acceleration | Dramatic starts |
| `EASE_OUT_CUBIC` | Strong deceleration | Dramatic endings |
| `EASE_IN_OUT_CUBIC` | Dramatic both ends | High-impact animations |

## Tick Methods

### tick()

Updates progress instantly without animation:

```java
bar.tick();      // Increment by 1
bar.tick(25);    // Increment by 25
bar.render();    // Show updated progress
```

Use for:
- High-frequency updates in tight loops
- When performance is critical
- Simple progress tracking

### tickAnimated()

Updates progress with smooth easing animation:

```java
bar.tickAnimated(50);  // Smooth animation to +50, auto-renders each frame
```

The method:
- Checks if the tick amount meets the threshold
- If yes: animates smoothly and renders each frame automatically
- If no: falls back to instant `tick()`

Use for:
- Large jumps in progress
- Drawing attention to updates
- Polished user experience

## Complete Examples

### File Download Progress

```java
EasingConfiguration easing = EasingConfiguration.immutableBuilder()
    .function(EasingFunction.EASE_OUT_QUAD)
    .duration(300)
    .frames(20)
    .threshold(5)
    .build();

ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("[cyan]:bar[/] :percent% | :progress/:total MB")
    .easing(easing)
    .build();

ProgressBar bar = Clique.progressBar(100, config);

// Simulate downloading in chunks
bar.tickAnimated(15);  // First chunk: 15MB (animated)
bar.tickAnimated(25);  // Second chunk: 25MB (animated)
bar.tickAnimated(30);  // Third chunk: 30MB (animated)
bar.tickAnimated(30);  // Final chunk: 30MB (animated)
```

### Batch Processing with Mixed Updates

```java
EasingConfiguration easing = EasingConfiguration.immutableBuilder()
    .function(EasingFunction.EASE_IN_OUT_CUBIC)
    .duration(800)
    .frames(40)
    .threshold(20)
    .build();

ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .format("[green]:bar[/] [:progress/:total tasks]")
    .easing(easing)
    .build();

ProgressBar bar = Clique.progressBar(1000, config);

// Large batch - smooth animation
bar.tickAnimated(150);

// Process items one by one - instant updates
for (int i = 0; i < 50; i++) {
    processItem();
    bar.tick();  // Instant, no animation overhead
}
bar.render();

// Another large batch - smooth animation
bar.tickAnimated(200);

// Complete remaining
bar.complete();
```

### Styled Progress with Animation

```java
EasingConfiguration easing = EasingConfiguration.immutableBuilder()
    .function(EasingFunction.EASE_OUT_SINE)
    .duration(600)
    .frames(30)
    .threshold(10)
    .build();

ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
    .styleRange(0, 30, "[red]:bar[/] :percent% [red, bold]Starting...[/]")
    .styleRange(30, 70, "[yellow]:bar[/] :percent% [yellow, bold]In Progress...[/]")
    .styleRange(70, 100, "[green]:bar[/] :percent% [green, bold]Almost Done![/]")
    .easing(easing)
    .build();

ProgressBar bar = Clique.progressBar(100, config);

bar.tickAnimated(25);   // Red phase (animated)
bar.tickAnimated(40);   // Transitions to yellow (animated)
bar.tickAnimated(35);   // Green phase (animated)
```

## Performance Considerations

### When to Use Easing

**Use easing when:**
- Progress jumps in large increments (batch processing)
- Updates are infrequent (a few per second)
- Visual polish matters
- You want to draw attention to progress changes

**Skip easing when:**
- Updates happen in tight loops (thousands per second)
- Progress increments by 1 frequently
- Performance is critical
- You prefer snappy, instant updates

### Optimizing Easing

**Balance frames and duration:**
```java
// Smooth but CPU-intensive
.duration(1000).frames(60)  // 60 FPS

// Good balance
.duration(500).frames(30)   // 30 FPS

// Snappier, lighter
.duration(300).frames(15)   // 15 FPS
```

**Use threshold wisely:**
```java
// Animate big jumps, skip small ones
.threshold(10)

// For very large operations, higher threshold
.threshold(100)
```

**Mix animated and instant ticks:**
```java
// Animate big changes
bar.tickAnimated(largeChunk);

// Instant for incremental updates
for (int i = 0; i < items; i++) {
    bar.tick();
}
bar.render();
```

## Things to Know

- **Blocking Behavior**: `tickAnimated()` blocks the current thread during animation. This is usually fine for CLI apps where the progress bar is the main focus.
- **Auto-Rendering**: Unlike `tick()`, `tickAnimated()` automatically calls `render()` for each frame. You don't need to call `render()` afterward unless you want to ensure the final state is displayed.
- **Threshold Check**: The threshold checks the *tick amount*, not the current progress. A tick of 5 on a bar at position 90 will still skip animation if threshold is 10.
- **Thread Interruption**: If the animation thread is interrupted, the progress bar will jump to the target value immediately.

## See Also

- [Progress Bars Documentation](progress-bars.md) - Core progress bar features
- [Markup Reference](markup-reference.md) - Styling progress bar output
- [Progress Bar Configuration](progress-bars.md#configuration) - General configuration options