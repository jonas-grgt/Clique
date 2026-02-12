module clique.themes {
    requires clique.spi;
    uses io.github.kusoroadeolu.clique.spi.CliqueTheme;
    provides io.github.kusoroadeolu.clique.spi.CliqueTheme
            with io.github.kusoroadeolu.clique.themes.CatppuccinLatteTheme,
                    io.github.kusoroadeolu.clique.themes.CatppuccinMochaTheme,
                    io.github.kusoroadeolu.clique.themes.DraculaTheme,
                    io.github.kusoroadeolu.clique.themes.NordTheme,
                    io.github.kusoroadeolu.clique.themes.GruvboxDarkTheme,
                    io.github.kusoroadeolu.clique.themes.GruvboxLightTheme,
                    io.github.kusoroadeolu.clique.themes.TokyoNightTheme;
}