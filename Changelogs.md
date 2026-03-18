# Changelog

## [3.1.0-beta] - 2026-03-17

### Added
- `Frame` component for layout composition — bordered container that vertically stacks nested Clique components. Supports `BoxType` border styles, titled borders, per-node alignment, and markup in string nodes and titles
- `Tree` component for hierarchical data with `├─, └─, │` connectors, arbitrary nesting, markup in labels, and guide styling via `TreeConfiguration`
- RGB ANSI code support via new interface
- Emoji support in `Box`, `Table`, and `Frame`
- `Box#customize()` — casts to `CustomizableBox` for fluent customization chaining
- `Clique#rgb(int, int, int)` and `Clique#rgb(int, int, int, boolean)` factory methods for creating RGB ANSI codes directly — returns `RGBAnsiCode` compatible with `Clique#registerStyle()`


### Fixed
- `AnsiStringParser#getOriginalString` now correctly strips ANSI codes, not just Clique parser tags

### Changed
- `noDimensions()` now throws `IllegalStateException` if `autoSize` is not enabled in `BoxConfiguration`
- `withDimensions()` now throws `IllegalArgumentException` for zero or negative values
  For both **Box** and **CustomizableBox**
- All box types (`DEFAULT`, `CLASSIC`, `ROUNDED`, `DOUBLE_LINE`) consolidated into a single `DefaultBox` implementation driven by `BorderChars`, eliminates the previous per-type subclass hierarchy
- All box types now support border customization — previously restricted to `DEFAULT` only

- `BorderStyle` string overloads added for `horizontalBorderStyles()`, `verticalBorderStyles()`, and `edgeBorderStyles()` — accepts markup style strings using the default delimiter
- `BorderStyle.BorderStyleBuilder` now has `uniformStyle(AnsiCode...)` and `uniformStyle(String)` for applying a single style across all border axes
- `AnsiStringParser` now exposes `ansiCodes(String)` — splits by the configured delimiter, resolves each token to an `AnsiCode`, and silently drops unrecognized styles

### Deprecated
- `addHeaders()` in favor of `Table#headers()` for cleaner and more concise chaining
- `addRows()` in favor of `Table#row()` for cleaner and more concise chaining
  For both **Table** and **CustomizableTable**
- `Clique#customizableBox()` overloads in favor of `Clique#box().customize()`
- `BoxConfiguration#centerPadding` due to unclear and incorrect semantics


### Deprecated

Report issues at: https://github.com/kusoroadeolu/Clique/issues

