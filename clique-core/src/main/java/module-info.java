module clique.core {
    requires clique.spi;
    requires jdk.xml.dom;
    uses io.github.kusoroadeolu.clique.spi.CliqueTheme;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme
            with io.github.kusoroadeolu.clique.themeloader.TestTheme;
}