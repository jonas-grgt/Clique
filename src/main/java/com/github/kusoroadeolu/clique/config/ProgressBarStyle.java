package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.indent.Indenter;

import java.util.function.Predicate;

public record ProgressBarStyle(Predicate<Integer> predicate, String format) {

    public boolean matches(int val){
        return predicate.test(val);
    }
}
