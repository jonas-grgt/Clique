# Clique v3.0.0
## Breaking Changes Ahead
### Known Issues
- Nesting other components in boxes cause issues
- Emojis still break existing components

### New Features
#### Progress Bars
- Brand new progress bar component with extensive customization options:
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
**After (v2.1.0-beta / v3.0.0):**
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

- Boxes and Tables no longer use `buildBox()` and `buildTable()` to get their strings, they now use `get()` along with other components unifying and simplifying the API

- `Clique.parser()` is now fully immutable after construction. `getOriginalString()` now requires for the string to be passed as a parameter to support this change fully i.e. `getOriginalString(someString)` 

**Migration:** Remove any intermediate variable assignments and chain `addHeaders()` directly after `Clique.table()` and  `width(), length()` after `Clique.boxes()`
### Documentation Updates
- Added comprehensive progress bar documentation
- Updated table examples to reflect new API
- Added easing animation guide

### Testing
This is a beta release. Please test the following:
- Table creation with the new HeaderBuilder API
- Progress bar rendering in your terminal
- Easing animations with different configurations

Report issues at: https://github.com/kusoroadeolu/Clique/issues

**Migration Path:**
- v2.0.0 → v2.1.0-beta: Test your code, update breaking changes
- v2.1.0-beta → v3.0.0: More breaking changes and stability improvements

### Next Steps
After beta testing period, stable release will be v3.0.0 with the same changes.