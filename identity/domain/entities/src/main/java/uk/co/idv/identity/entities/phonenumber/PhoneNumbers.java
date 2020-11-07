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

    private final Collection<PhoneNumber> numbers;

    public PhoneNumbers(PhoneNumber... numbers) {
        this(Arrays.asList(numbers));
    }

    public PhoneNumbers(Collection<PhoneNumber> values) {
        this.numbers = new LinkedHashSet<>(values);
    }

    public Iterator<PhoneNumber> iterator() {
        return numbers.iterator();
    }

    public Stream<PhoneNumber> stream() {
        return numbers.stream();
    }

    public PhoneNumbers add(PhoneNumbers others) {
        Collection<PhoneNumber> mergedNumbers = new LinkedHashSet<>();
        mergedNumbers.addAll(this.numbers);
        mergedNumbers.addAll(others.numbers);
        return new PhoneNumbers(mergedNumbers);
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }

}
