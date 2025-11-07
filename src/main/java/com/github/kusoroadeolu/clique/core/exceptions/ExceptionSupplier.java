package com.github.kusoroadeolu.clique.core.exceptions;

@FunctionalInterface
public interface ExceptionSupplier<T> {
    T supply() throws IllegalArgumentException;
}
