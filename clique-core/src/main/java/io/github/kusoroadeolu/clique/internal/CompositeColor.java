package io.github.kusoroadeolu.clique.internal;

import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Collection;

public class CompositeColor implements AnsiCode {
    private final String ansiSequence;

    public CompositeColor(AnsiCode... codes) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(codes).forEach(c -> sb.append(c.ansiSequence()));
        ansiSequence = sb.toString();
    }

    public CompositeColor(Collection<AnsiCode> codes) {
        StringBuilder sb = new StringBuilder();
        codes.forEach(c -> sb.append(c.ansiSequence()));
        ansiSequence = sb.toString();
    }

    @Override
    public String ansiSequence() {
        return ansiSequence;
    }
}
