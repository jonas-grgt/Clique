package boxes.interfaces;

@FunctionalInterface
public interface ExceptionSupplier<T> {
    T supply() throws IllegalArgumentException;
}
