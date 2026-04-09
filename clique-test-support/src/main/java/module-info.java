import io.github.kusoroadeolu.clique.testsupport.TestTheme;

module clique.test.support {
    requires clique.spi;
    requires clique.core;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme with TestTheme;
}