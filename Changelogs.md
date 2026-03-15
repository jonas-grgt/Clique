# Clique v3.1.0-beta
- Added `Frame` component for layout composition — a bordered container that vertically stacks nested Clique components without interfering with their rendering. Supports all BoxType border styles, optional titled borders, per-node alignment, and markup in string nodes and titles
- AnsiStringParser#getOriginalString actually strips ANSI codes now, not only Clique parser tags
- Added RGB ANSI code interface
- Emojis can now be used in boxes, tables and frames
- Added `Tree` component for displaying hierarchical data with `├─, └─, │` connector lines, supports arbitrary nesting, markup in labels, and guide connector styling via TreeConfiguration

Report issues at: https://github.com/kusoroadeolu/Clique/issues

