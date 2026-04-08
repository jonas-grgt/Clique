package io.github.kusoroadeolu.clique.style;

import io.github.kusoroadeolu.clique.ansi.BackgroundCode;
import io.github.kusoroadeolu.clique.ansi.ColorCode;
import io.github.kusoroadeolu.clique.ansi.RGBColor;
import io.github.kusoroadeolu.clique.ansi.StyleCode;
import io.github.kusoroadeolu.clique.core.documentation.Experimental;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.parser.PredefinedStyleContext;
import io.github.kusoroadeolu.clique.parser.StyleContext;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A lightweight, functional, chainable ANSI string builder.
 *
 * <p>Similar in spirit to <a href="https://github.com/chalk/chalk">Chalk</a> — each method
 * returns a new {@code Ink} instance, leaving the original unchanged. Call {@link #on(String)}
 * to produce the final styled string.
 *
 * <p>Outputs raw ANSI escape sequences directly — no markup parser involved.
 *
 * <p>Usage:
 * <pre>{@code
 * Clique.ink().red().bold().on("Error")
 *
 * Clique.ink().of("ctp_mauve").bold().on("Hello")
 * }</pre>
 *
 * @since 4.0.0
 */
@Experimental(since = "4.0.0")
public final class Ink {

    private final List<AnsiCode> codes;
    private final StyleContext context;

    public Ink() {
        this(StyleContext.NONE);
    }

    public Ink(StyleContext context) {
        Objects.requireNonNull(context, "Style context cannot be null");
        this.codes = Collections.emptyList();
        this.context = context;
    }

    Ink(List<AnsiCode> codes, StyleContext context) {
        this.codes = Collections.unmodifiableList(codes);
        this.context = context;
    }

    private Ink with(AnsiCode code) {
        List<AnsiCode> next = new ArrayList<>(codes);
        next.add(code);
        return new Ink(next, context);
    }


    /**
     * Applies the accumulated ANSI codes to the given value and returns the styled string.
     * Appends a reset sequence at the end.
     *
     * @param value the value to style; {@code toString()} is called on it
     * @throws NullPointerException if value is null
     * @return the styled string with ANSI sequences applied, or just
     *         {@code value.toString()} if no styles were accumulated
     */
    public String on(String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        if (codes.isEmpty()) return value;

        StringBuilder sb = new StringBuilder();
        for (AnsiCode code : codes) {
            sb.append(code.ansiSequence());
        }
        sb.append(value);
        sb.append(StyleCode.RESET.ansiSequence());
        return sb.toString();
    }



    /**
     * Adds a named style, looked up from the instance's {@link StyleContext}
     * and {@link PredefinedStyleContext}. Throws {@code UndefinedStyleException} if not found.
     *
     * @param style the registered style name (e.g. {@code "ctp_mauve"})
     * @throws NullPointerException if style is null
     * @return a new {@code Ink} instance with the style accumulated
     */
    public Ink of(String style) {
        Objects.requireNonNull(style, "Style cannot be null");
        AnsiCode code = PredefinedStyleContext.getOrThrow(style, context);
        return with(code);
    }


    /**
     * Adds a named style, looked up from the instance's {@link StyleContext}
     * and {@link PredefinedStyleContext}. Throws {@code UndefinedStyleException} if not found.
     *
     * @param code the AnsiCode to be applied
     * @throws NullPointerException if style is null
     * @return a new {@code Ink} instance with the style accumulated
     */
    public Ink of(AnsiCode code) {
        Objects.requireNonNull(code, "Ansi code cannot be null");
        return with(code);
    }


    public Ink black()         { return with(ColorCode.BLACK);          }
    public Ink red()           { return with(ColorCode.RED);            }
    public Ink green()         { return with(ColorCode.GREEN);          }
    public Ink yellow()        { return with(ColorCode.YELLOW);         }
    public Ink blue()          { return with(ColorCode.BLUE);           }
    public Ink magenta()       { return with(ColorCode.MAGENTA);        }
    public Ink cyan()          { return with(ColorCode.CYAN);           }
    public Ink white()         { return with(ColorCode.WHITE);          }

    public Ink rgb(int red, int green, int blue) {return with(new RGBColor(red, green, blue));}

    // Bright foreground colors
    public Ink brightBlack()   { return with(ColorCode.BRIGHT_BLACK);   }
    public Ink brightRed()     { return with(ColorCode.BRIGHT_RED);     }
    public Ink brightGreen()   { return with(ColorCode.BRIGHT_GREEN);   }
    public Ink brightYellow()  { return with(ColorCode.BRIGHT_YELLOW);  }
    public Ink brightBlue()    { return with(ColorCode.BRIGHT_BLUE);    }
    public Ink brightMagenta() { return with(ColorCode.BRIGHT_MAGENTA); }
    public Ink brightCyan()    { return with(ColorCode.BRIGHT_CYAN);    }
    public Ink brightWhite()   { return with(ColorCode.BRIGHT_WHITE);   }


    public Ink bgBlack()         { return with(BackgroundCode.BLACK);          }
    public Ink bgRed()           { return with(BackgroundCode.RED);            }
    public Ink bgGreen()         { return with(BackgroundCode.GREEN);          }
    public Ink bgYellow()        { return with(BackgroundCode.YELLOW);         }
    public Ink bgBlue()          { return with(BackgroundCode.BLUE);           }
    public Ink bgMagenta()       { return with(BackgroundCode.MAGENTA);        }
    public Ink bgCyan()          { return with(BackgroundCode.CYAN);           }
    public Ink bgWhite()         { return with(BackgroundCode.WHITE);          }

    public Ink bgRgb(int red, int green, int blue) {return with(new RGBColor(red, green, blue, true));}


    public Ink brightBgBlack()   { return with(BackgroundCode.BRIGHT_BLACK);   }
    public Ink brightBgRed()     { return with(BackgroundCode.BRIGHT_RED);     }
    public Ink brightBgGreen()   { return with(BackgroundCode.BRIGHT_GREEN);   }
    public Ink brightBgYellow()  { return with(BackgroundCode.BRIGHT_YELLOW);  }
    public Ink brightBgBlue()    { return with(BackgroundCode.BRIGHT_BLUE);    }
    public Ink brightBgMagenta() { return with(BackgroundCode.BRIGHT_MAGENTA); }
    public Ink brightBgCyan()    { return with(BackgroundCode.BRIGHT_CYAN);    }
    public Ink brightBgWhite()   { return with(BackgroundCode.BRIGHT_WHITE);   }



    public Ink bold()            { return with(StyleCode.BOLD);             }
    public Ink dim()             { return with(StyleCode.DIM);              }
    public Ink italic()          { return with(StyleCode.ITALIC);           }
    public Ink underline()       { return with(StyleCode.UNDERLINE);        }
    public Ink doubleUnderline() { return with(StyleCode.DOUBLE_UNDERLINE); }
    public Ink strikethrough()   { return with(StyleCode.STRIKETHROUGH);    }
    public Ink reverseVideo()    { return with(StyleCode.REVERSE_VIDEO);    }
    public Ink invisible()       { return with(StyleCode.INVISIBLE_TEXT);   }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Ink ink = (Ink) o;
        return Objects.equals(codes, ink.codes) && Objects.equals(context, ink.context);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(codes);
        result = 31 * result + Objects.hashCode(context);
        return result;
    }


}