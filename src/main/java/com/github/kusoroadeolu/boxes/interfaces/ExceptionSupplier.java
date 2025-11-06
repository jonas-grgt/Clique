package com.github.kusoroadeolu.boxes.interfaces;

@FunctionalInterface
public interface ExceptionSupplier<T> {
    T supply() throws IllegalArgumentException;
}
