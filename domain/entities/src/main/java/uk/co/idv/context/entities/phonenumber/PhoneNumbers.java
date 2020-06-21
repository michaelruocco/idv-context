package uk.co.idv.context.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class PhoneNumbers implements Iterable<PhoneNumber> {

    private final Collection<PhoneNumber> numbers;

    public PhoneNumbers(PhoneNumber... numbers) {
        this(Arrays.asList(numbers));
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

    public PhoneNumbers getMobileNumbers() {
        return new PhoneNumbers(numbers.stream()
                .filter(PhoneNumber::isMobile)
                .collect(Collectors.toList()));
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }

}
