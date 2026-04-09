# ItemList

`ItemList` lets you build nested, symbol-driven lists with full markup support. Each item gets a symbol, some content, and optionally a sublist — and the whole thing composes cleanly without any manual level tracking.

> **Heads up:** If you're trying to render a file tree or parent-child hierarchy with `├─` / `└─` connectors, [`Tree`](tree.md) is what you want. `ItemList` is for when *you* decide the structure — task lists, CLI output, menus, anything where the symbols are part of the design.

---

## Basic Usage

```java
Clique.list()
    .item("•", "Buy groceries")
    .item("•", "Write tests")
    .item("•", "Ship it")
    .render();
```

```
• Buy groceries
• Write tests
• Ship it
```

---

## Nesting

Pass a sublist as a third argument to `item()`. Config cascades automatically — no need to pass it at every level.

```java
Clique.list()
    .item("→", "Backend",
        Clique.list()
            .item("✓", "Auth service")
            .item("✓", "User service")
            .item("✗", "[red]Payment gateway[/]")
    )
    .item("→", "Frontend",
        Clique.list()
            .item("✓", "Landing page")
            .item("✗", "[red]Dashboard[/]")
    )
    .render();
```

```
→ Backend
  ✓ Auth service
  ✓ User service
  ✗ Payment gateway
→ Frontend
  ✓ Landing page
  ✗ Dashboard
```

---

## `item()` Overloads

```java
.item("•", "content")                          // symbol + content
.item("•", "content", Clique.list()...)        // symbol + content + sublist
```

Symbols are full markup strings — `"[red]✗[/]"` is valid.

---

## Getting the Output

```java
list.render();        // prints to stdout
String s = list.get(); // returns the rendered string
```

---

## Configuration

```java
ItemListConfiguration config = ItemListConfiguration.builder()
    .indentSize(4)            // spaces per level, default: 2
    .symbolSpacing(2)         // spaces between symbol and content, default: 1
    .parser(Clique.parser())  // default: new MarkupParser()
    .build();

Clique.list(config)
    .item("▸", "[bold]Section[/]",
        Clique.list()
            .item("–", "Subsection item")
    )
    .render();
```

### Configuration Options

| Option        | Default              | Constraint               |
|---------------|----------------------|--------------------------|
| indentSize    | 2                    | Cannot be less than 1    |
| symbolSpacing | 1                    | Cannot be negative       |
| parser        | MarkupParser.DEFAULT | —                      |

Config set on a parent list cascades into all nested sublists automatically.

---

## Markup Support

Markup works anywhere — symbols, content, or both:

```java
Clique.list()
    .item("[cyan]▸[/]", "[bold]Section Title[/]",
        Clique.list()
            .item("[green]✓[/]", "Thing that worked")
            .item("[yellow]~[/]", "Thing that kind of worked")
            .item("[red]✗[/]", "Thing that did not work")
    )
    .render();
```

---

## Examples

### Sprint Board

```java
Clique.list()
    .item("→", "[bold]Sprint 12[/]",
        Clique.list()
            .item("[green]✓[/]", "[dim, strike]Auth service[/]")
            .item("[green]✓[/]", "[dim, strike]User profile page[/]")
            .item("[yellow]~[/]", "Notification system — [yellow]in review[/]",
                Clique.list()
                    .item("!", "Waiting on design sign-off")
            )
            .item("[red]✗[/]", "[red]Payment integration[/]",
                Clique.list()
                    .item("!", "Stripe keys not in env")
                    .item("!", "Webhook endpoint missing")
            )
    )
    .render();
```

### Build Report

```java
Clique.list()
    .item("[bold]>[/]", "[bold, cyan]Build Report — v2.4.1[/]",
        Clique.list()
            .item("[green]✓[/]", "Compiled 42 files in 1.3s")
            .item("[green]✓[/]", "108 tests passed")
            .item("[yellow]⚠[/]", "2 deprecation warnings",
                Clique.list()
                    .item("–", "StringUtils.format() — use formatAndReset()")
                    .item("–", "Flag enum — superseded by markup symbols")
            )
            .item("[red]✗[/]", "Coverage at 74% — threshold is 80%")
    )
    .render();
```

### Nested CLI Menu

```java
ItemListConfiguration config = ItemListConfiguration.builder()
    .indentSize(3)
    .build();

Clique.list(config)
    .item("▸", "[cyan, bold]Main Menu[/]",
        Clique.list()
            .item("▸", "File",
                Clique.list()
                    .item("–", "New")
                    .item("–", "Open")
                    .item("–", "Save")
            )
            .item("▸", "Edit",
                Clique.list()
                    .item("–", "Cut")
                    .item("–", "Copy")
                    .item("–", "Paste")
            )
            .item("▸", "View")
    )
    .render();
```