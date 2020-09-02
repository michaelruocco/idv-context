package uk.co.idv.identity.entities.emailaddress;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class EmailAddresses implements Iterable<String> {

    private final Collection<String> values;

    public EmailAddresses(String... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    public Stream<String> stream() {
        return values.stream();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public EmailAddresses add(EmailAddresses others) {
        Collection<String> mergedValues = new LinkedHashSet<>();
        mergedValues.addAll(this.values);
        mergedValues.addAll(others.values);
        return new EmailAddresses(mergedValues);
    }

}
