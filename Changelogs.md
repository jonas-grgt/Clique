# Clique v3.0.0
## Breaking Changes Ahead
### Known Issues
- Nesting other components in boxes cause issues
- Emojis still break existing components

### New Features
#### Progress Bars
- Brand-new progress bar component with extensive customization options:
- Multiple predefined styles (Blocks, Lines, Bold, Classic, Dots)
- Custom format strings with placeholders (:bar, :percent, :elapsed, :remaining)
- Dynamic styling based on progress ranges
- Smooth easing animations with multiple easing functions
- Full markup support

```java
ProgressBar bar = Clique.progressBar(100, DefaultProgressBarStyle.BLOCKS);
bar.tick(50);
bar.render();
```
See the Progress Bars documentation for details.

### Improvements
**Default Configurations:** All configuration objects now have public static defaults for convenience:
- TableConfiguration.DEFAULT
- BoxConfiguration.DEFAULT
- IndenterConfiguration.DEFAULT
- ParserConfiguration.DEFAULT
- ProgressBarConfiguration.DEFAULT + more...

### Better Object Contracts
All components and configurations now properly implement:
- `equals()` - Structural equality checks
- `hashCode()` - Consistent hashing
- `toString()` - Readable debug output

### Breaking Changes
- **Compile Time Header Safety:** Tables and their customizable counter-parts now enforce header-first construction at compile time using a builder pattern. This prevents runtime errors from adding rows before headers.
Boxes and customizable boxes also enforce width and length construction using builder batter

**Before (v2.0.0):**
```java
Table table = Clique.table(TableType.DEFAULT);
table.addHeaders("Name", "Age", "Class")
    .addRows("John", "25", "Class A");
```
**After (v3.0.0):**
```java
TableHeaderBuilder builder = Clique.table(TableType.DEFAULT);
     builder.addHeaders("Name", "Age", "Class")  // Must be called first
           .addRows("John", "25", "Class A");
```

**Before (v2.0.0):**
```java
Box box = Clique.box(BoxType.CLASSIC);
box.width(5).length(5) //Width, Length
    .content("Class A");
```
**After (v3.0.0):**
```java
BoxDimensionBuilder box = Clique.box(BoxType.CLASSIC);
box.withDimensions(5, 5) //Width, Length 
    .content("Class A");

box1.withNoDimensions().content("Class A")
```

**Before (v2.0.0):**
```java
StyleBuilder builder = new DefaultStyleBuilder();
builder.formatReset("sometext", StyleCode.DIM);
```
**After (v3.0.0):**
```java
StyleBuilder builder = new DefaultStyleBuilder();
builder.formatAndReset("sometext", StyleCode.DIM);
```

- Boxes and Tables no longer use `buildBox()` and `buildTable()` to get their strings, they now use `get()` along with other components unifying and simplifying the API

- `Clique.parser()` is now fully immutable after construction. `getOriginalString()` now requires for the string to be passed as a parameter to support this change fully i.e. `getOriginalString(someString)` 

- **Removed Mutators:** All deprecated setter methods on `BoxConfiguration`, `BorderStyle`, `IndenterConfiguration`, `ParserConfiguration`, and `TableConfiguration` have been removed.

- **Builder Pattern Enforcement:** You can no longer modify a configuration object once it is created.

- **Static Factory Removal:** Removed the legacy `.builder()` static methods from configuration classes to favor a unified building experience.

- **Namespace Migration (com to io)**: To align with modern publishing standards, the base package has been renamed.
</br> Old: com.github.kusoroadeolu.clique
</br> New: io.github.kusoroadeolu.clique
**Action Required:** Global search and replace `com.github.kusoroadeolu` with `io.github.kusoroadeolu` in your imports.

- **Multi-Module Project Structure:** The project has been split into three modules to reduce footprint and improve extensibility.
</br> **clique-spi:** Theme interface definitions 
</br> **clique-core:** The main library
</br> **clique-themes:** The optional theme pack (Dracula, Nord, etc.).


#### Refactored API
- **Interface Slimming:** `Box` and `AnsiStringParser` interfaces no longer contain .configuration() methods. Configuration is now strictly handled at the instantiation/builder phase.

- **Explicit Exceptions:** The `DeprecatedMethodException` has been phased out as those methods no longer exist in the bytecode.

**Migration:** Remove any intermediate variable assignments and chain `addHeaders()` directly after `Clique.table()` and  `width(), length()` after `Clique.boxes()`

### Documentation Updates
- Added comprehensive progress bar documentation
- Updated table examples to reflect new API
- Added easing animation guide
- Also note that you can still get previous versions on jitpack but not Maven Central, though future versions would be released only on Maven central

Report issues at: https://github.com/kusoroadeolu/Clique/issues

