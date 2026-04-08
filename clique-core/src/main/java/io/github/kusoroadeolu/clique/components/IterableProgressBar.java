package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.configuration.ProgressBarConfiguration;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @since 3.2.1
 * */
@Stable(since = "3.2.1")
public class IterableProgressBar<T>  implements Iterable<T>{
    private final Iterator<T> iterator;
    private boolean consumed = false;
    final ProgressBar progressBar;


    public IterableProgressBar(Collection<T> collection, ProgressBarConfiguration configuration){
        Objects.requireNonNull(collection, "Collection cannot be null");
        Objects.requireNonNull(configuration, "Parser configuration cannot be null");
        this.iterator = collection.iterator();
        progressBar = new ProgressBar(collection.size(), configuration);
    }


    public IterableProgressBar(Collection<T> collection){
        this(collection, ProgressBarConfiguration.DEFAULT);
    }


    @Override
    public Iterator<T> iterator() {
        if (consumed) throw new IllegalStateException("IterableProgressBar can only be iterated once");
        consumed = true;
        return new ProgressBarIterator<>(this);
    }

    public boolean isDone(){
        return progressBar.isDone();
    }


    private record ProgressBarIterator<T>(IterableProgressBar<T> iterableProgressBar) implements Iterator<T> {

        @Override
            public boolean hasNext() {
                return iterableProgressBar.iterator.hasNext();
            }

            @Override
            public T next() {
                T item = iterableProgressBar.iterator.next();
                iterableProgressBar.progressBar.tick();
                return item;
            }
        }
}
