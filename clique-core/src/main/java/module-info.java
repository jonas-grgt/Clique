module clique.core {
    requires clique.spi;
    requires jdk.xml.dom;
    requires clique.core;
    uses io.github.kusoroadeolu.clique.spi.CliqueTheme;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme
            with io.github.kusoroadeolu.clique.themeloader.TestTheme;
}