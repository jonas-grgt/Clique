import io.github.kusoroadeolu.clique.internal.themeloader.TestTheme;

module clique.core {
    requires clique.spi;
    uses io.github.kusoroadeolu.clique.spi.CliqueTheme;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme
            with TestTheme;
}