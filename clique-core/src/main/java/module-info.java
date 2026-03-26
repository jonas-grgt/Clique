module clique.core {
    requires clique.spi;
    requires java.xml;
    requires clique.core;
    uses io.github.kusoroadeolu.clique.spi.CliqueTheme;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme
            with io.github.kusoroadeolu.clique.themeloader.TestTheme;
}