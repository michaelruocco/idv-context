package uk.co.idv.context.entities.emailaddress;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class EmailAddresses implements Iterable<String> {

    private final Collection<String> values;

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    public Stream<String> stream() {
        return values.stream();
    }

}
