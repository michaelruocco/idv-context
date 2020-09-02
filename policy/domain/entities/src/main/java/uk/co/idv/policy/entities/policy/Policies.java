package uk.co.idv.policy.entities.policy;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Policies<T extends Policy> implements Iterable<T> {

    private final Collection<T> values;

    public Policies(T... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    public Stream<T> stream() {
        return values.stream();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Policies<T> getApplicable(PolicyRequest request) {
        return new Policies<>(values.stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList()));
    }

    public T getHighestPriority() {
        return values.stream()
                .max(Comparator.comparing(T::getPriority))
                .orElseThrow(EmptyPoliciesException::new);
    }

}
