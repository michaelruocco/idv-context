package uk.co.idv.method.entities.eligibility;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class EligibilityFutures implements Iterable<CompletableFuture<Eligibility>> {

    private final Collection<CompletableFuture<Eligibility>> futures;

    @Override
    public Iterator<CompletableFuture<Eligibility>> iterator() {
        return futures.iterator();
    }

    public boolean isEmpty() {
        return futures.isEmpty();
    }

    public boolean allDone() {
        return isEmpty() || all().isDone();
    }

    public CompletableFuture<Void> all() {
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

}
