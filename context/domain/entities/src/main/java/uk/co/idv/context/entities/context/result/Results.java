package uk.co.idv.context.entities.context.result;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

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

}
