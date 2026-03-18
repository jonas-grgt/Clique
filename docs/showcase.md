# Showcase

A collection of fun things you can build with Clique. All examples are self-contained and copy-paste ready.

---

## Live Clock in a Frame

A live clock that updates in place using a `Component` — a functional interface that gets evaluated on every render.

```java
public class LiveClock {
    public static void main(String[] args) throws InterruptedException {
        Component clock = () -> {
            var now = java.time.LocalTime.now();
            return "[cyan, bold]%02d:%02d:%02d[/]".formatted(
                    now.getHour(), now.getMinute(), now.getSecond()
            );
        };

        Frame frame = Clique.frame(BoxType.ROUNDED)
                .title("[bold]Live Clock[/]")
                .nest(clock);

        int lastSize = 0;

        while (true) {
            if (lastSize > 0) {
                System.out.print("\033[" + lastSize + "A");
            }

            String rendered = frame.get();
            var lines = rendered.lines().toList();

            for (var line : lines) {
                System.out.println("\r" + line);
            }

            lastSize = lines.size();
            Thread.sleep(1000);
        }
    }
}
```

---

## Scrolling Marquee Title

Scroll text through the title bar of a frame using substring wrapping. Good for alerts, status messages, or just making things look alive.

```java
public class MarqueeTitle {
    public static void main(String[] args) throws InterruptedException {
        String text = "[ SYSTEM ALERT — MEMORY HIGH — CPU SPIKING — DISK LOW ]  ";

        // Fixed-width anchor so the frame doesn't collapse
        Component dummy = () -> "                                        ";

        Frame frame = Clique.frame(BoxType.ROUNDED)
                .nest(dummy);

        int[] offset = {0};
        int lastSize = 0;

        while (true) {
            if (lastSize > 0) {
                System.out.print("\033[" + lastSize + "A");
            }

            String scrolled = text.substring(offset[0]) + text.substring(0, offset[0]);
            frame.title("[bold, red]" + scrolled + "[/]", FrameAlign.LEFT);

            String rendered = frame.get();
            var lines = rendered.lines().toList();

            for (var line : lines) {
                System.out.println("\r" + line);
            }

            lastSize = lines.size();
            offset[0] = (offset[0] + 1) % text.length();
            Thread.sleep(120);
        }
    }
}
```

---

## Progress Bar with Live Clock

Combine a progress bar and a live clock inside a single frame. Both update on every tick.

```java
public class ProgressWithClock {
    public static void main(String[] args) throws InterruptedException {
        ProgressBar bar = Clique.progressBar(100, ProgressBarPreset.BLOCKS);

        Component clock = () -> {
            var now = java.time.LocalTime.now();
            return "[dim]%02d:%02d:%02d[/]".formatted(
                    now.getHour(), now.getMinute(), now.getSecond()
            );
        };

        Frame frame = Clique.frame(BoxType.ROUNDED)
                .title("[bold, cyan]My App[/]")
                .nest(clock, FrameAlign.RIGHT)
                .nest("[dim]Processing files...[/]")
                .nest(bar);

        int lastSize = 0;

        while (!bar.isDone()) {
            if (lastSize > 0) {
                System.out.print("\033[" + lastSize + "A");
            }

            bar.tick(2);
            String rendered = frame.get();
            var lines = rendered.lines().toList();

            for (var line : lines) {
                System.out.println("\r" + line);
            }

            lastSize = lines.size();
            Thread.sleep(100);
        }
    }
}
```

---

> More recipes coming soon. Have something cool you built with Clique? Feel free to open a PR.