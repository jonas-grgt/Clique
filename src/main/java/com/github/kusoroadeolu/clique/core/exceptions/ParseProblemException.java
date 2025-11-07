package com.github.kusoroadeolu.clique.core.exceptions;

public class ParseProblemException extends RuntimeException {
    public ParseProblemException(String message) {
        super(message);
    }

    public ParseProblemException(String message, Throwable cause) {
        super(message, cause);
    }
}
