package uk.co.idv.identity.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
public class PhoneNumbers implements Iterable<PhoneNumber> {

    private final Collection<PhoneNumber> values;

    public PhoneNumbers(PhoneNumber... values) {
        this(Arrays.asList(values));
    }

    public PhoneNumbers(Collection<PhoneNumber> values) {
        this.values = new LinkedHashSet<>(values);
    }

    public Iterator<PhoneNumber> iterator() {
        return values.iterator();
    }

    public Stream<PhoneNumber> stream() {
        return values.stream();
    }

    public PhoneNumbers add(PhoneNumbers others) {
        Collection<PhoneNumber> mergedValues = new LinkedHashSet<>();
        mergedValues.addAll(this.values);
        mergedValues.addAll(others.values);
        return new PhoneNumbers(mergedValues);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

}
