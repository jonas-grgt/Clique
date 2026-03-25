**# Changelog

## Clique [3.1.0] - 2026-03-21

### Added
- `Frame` component for layout composition — bordered container that vertically stacks nested Clique components. Supports `BoxType` border styles, titled borders, per-node alignment, and markup in string nodes and titles
- `Tree` component for hierarchical data with `├─, └─, │` connectors, arbitrary nesting, markup in labels, and guide styling via `TreeConfiguration`
- RGB ANSI code support via new interface
- Emoji support in `Box`, `Table`, and `Frame`
- `Box` now allows for fluent customization chaining
- `Clique#rgb(int, int, int)` and `Clique#rgb(int, int, int, boolean)` factory methods for creating RGB ANSI codes directly — returns `RGBAnsiCode` compatible with `Clique#registerStyle()`


### Fixed
- `AnsiStringParser#getOriginalString` now correctly strips ANSI codes, not just Clique parser tags
- `Table#removeColumn(row, col)` does not throw an exception if col idx = 0

### Changed
- `noDimensions()` now throws `IllegalStateException` if `autoSize` is not enabled in `BoxConfiguration`
- `withDimensions()` now throws `IllegalArgumentException` for zero or negative values
  For both **Box** and **CustomizableBox**
- All box types (`DEFAULT`, `CLASSIC`, `ROUNDED`, `DOUBLE_LINE`) consolidated into a single `DefaultBox` implementation driven by `BorderChars`, eliminates the previous per-type subclass hierarchy
- All box types now support border customization — previously restricted to `DEFAULT` only

- `BorderStyle` string overloads added for `horizontalBorderStyles()`, `verticalBorderStyles()`, and `edgeBorderStyles()` — accepts markup style strings using the default delimiter, without the markup borders i.e. `[]`
- `BorderStyle.BorderStyleBuilder` now has `uniformStyle(AnsiCode...)` and `uniformStyle(String)` for applying a single style across all border axes
- Default `BoxType` changed from `BoxType.DEFAULT` to `BoxType.ROUNDED`, more visually appealing out of the box(for both frame and box)
- Default `TableType` changed from `TableType.DEFAULT` to `TableType.BOX_DRAW`, same reason


### Deprecated
- `addHeaders()` in favor of `Table#headers()` for cleaner and more concise chaining
- `addRows()` in favor of `Table#row()` for cleaner and more concise chaining
- `Clique#customizableBox()` overloads in favor of using `BorderStyle#horizontalChar()`, `BorderStyle#verticalChar()`, `BorderStyle#cornerChar()` config methods
- `Clique#customizableTable()` overloads in favor of using `BorderStyle#horizontalChar()`, `BorderStyle#verticalChar()`, `BorderStyle#cornerChar()` config methods
- `BoxConfiguration#centerPadding` due to unclear and incorrect semantics
- `BorderStyle#getHorizontalBorderStyles()` in favor of `getHorizontalStyle()`
- `BorderStyle#getEdgeBorderStyles()` in favor of `getCornerStyle()`
- `BorderStyle#getVerticalBorderStyles()` in favor of `getVerticalStyle()`
- `BorderStyleBuilder#horizontalBorderStyles()` in favor of `horizontalStyle()`
- `BorderStyleBuilder#edgeBorderStyles()` in favor of `cornerStyle()`
- `BorderStyleBuilder#verticalBorderStyles()` in favor of `verticalStyle()`

## clique-spi [1.0.2] - 2026-03-21
Changes to the SPI module in this release.
### Changed
- Introduction of `RGBAnsiCode` interface

---

# Changelog

## Clique [3.1.1] - 2026-03-24

### Added
- `BorderStyle` overloads for `Clique#frame()`, `Clique#box()`, and `Clique#table()`
- `EasingConfiguration` overload for `Clique#progressBar()`
- `ProgressBarConfiguration#fromPreset()` — returns a configuration builder for further customization of progress bar presets

### Changed
- `EasingConfiguration.DEFAULT` now uses sensible animation defaults (`EASE_OUT_QUAD`, 500ms, 20 frames, threshold 5)
- Added `EasingConfiguration.DISABLED` as an explicit no-op constant
- `AnsiDetector#autoDetect()` now checks `CLICOLOR_FORCE` and `COLORTERM` environment variables
- `NO_COLOR` detection now correctly requires a non-empty value per spec
- Added `WT_SESSION` check for Windows Terminal ANSI support
- `AnsiDetector#ansiEnabled()` now returns a cached value instead of re-running detection on every call
- `AnsiDetector#enableCliqueColors()` and `#disableCliqueColors()` now update the cache directly


### Fixed
- Fixed an issue with `Indenter` where, if an empty(not blank) flag was passed as the current flag, it'd take precedence over the default configuration flag

## clique-spi [1.0.3] - 2026-03-24

---
...

# Changelog

## Clique [3.1.2] - 2026-03-26
**NOTE:** Most of these changes are internal changes and don't affect the public facing API

### Changed
- `Frame` padding is now applied symmetrically on both sides for all alignment values, matching `Box` padding behaviour
- `BorderStyle#styleBuilder()` now returns a fresh `StyleBuilder` instance per call instead of a cached shared instance, eliminates mutable shared state on an otherwise immutable config object

### Removed
- Word wrap pipeline from `AbstractBox` -> `wrapWord()`, `adjustBox()` removed from `AbstractBox`; `wrapLongString()`, `getActiveAnsiCodes()`, `getStyledEndIndex()` removed from `StringUtils`; `splitAndPreserveAnsi()` removed from `BoxUtils`

### Added
- `resolveLines()` -> splits box content on `\n`, returns `List<Cell>`
- `resolveDimensions()` -> validates explicit dimensions or computes them for `autoSize`
- `Box#content(String, TextAlign)` and `Box#content(Object, TextAlign)` -> convenience overloads for setting content and alignment inline

Report issues at: https://github.com/kusoroadeolu/Clique/issues**