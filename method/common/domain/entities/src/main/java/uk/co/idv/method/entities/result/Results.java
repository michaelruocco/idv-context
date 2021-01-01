package uk.co.idv.method.entities.result;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class Results implements Iterable<Result> {

    private final Collection<Result> values;

    public Results(Result... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Result> iterator() {
        return values.iterator();
    }

    public Results add(Result result) {
        Collection<Result> updated = new ArrayList<>(values);
        updated.add(result);
        return new Results(updated);
    }

    public Results byName(String methodName) {
        return new Results(values.stream()
                .filter(result -> result.isFor(methodName))
                .collect(Collectors.toList())
        );
    }

    public boolean containsSuccessful(String methodName) {
        return values.stream()
                .filter(result -> result.isFor(methodName))
                .anyMatch(Result::isSuccessful);
    }

    public boolean containsSuccessful() {
        return values.stream().anyMatch(Result::isSuccessful);
    }

    public int size() {
        return values.size();
    }

}
