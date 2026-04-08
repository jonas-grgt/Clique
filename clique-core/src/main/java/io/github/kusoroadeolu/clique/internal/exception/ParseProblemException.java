package io.github.kusoroadeolu.clique.internal.exception;

public class ParseProblemException extends RuntimeException {
    public ParseProblemException(String message) {
        super(message);
    }

    public ParseProblemException(String message, Throwable cause) {
        super(message, cause);
    }
}
