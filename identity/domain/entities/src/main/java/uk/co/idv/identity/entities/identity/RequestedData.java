package uk.co.idv.identity.entities.identity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
public class RequestedData implements Iterable<String> {

    public static final String EMAIL_ADDRESSES = "email-addresses";
    public static final String PHONE_NUMBERS = "phone-numbers";

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private final Collection<String> items;

    public static RequestedData emailAddressesOnly() {
        return new RequestedData(EMAIL_ADDRESSES);
    }

    public static RequestedData phoneNumbersOnly() {
        return new RequestedData(PHONE_NUMBERS);
    }

    public RequestedData(String... items) {
        this(Arrays.asList(items));
    }

    @Override
    public Iterator<String> iterator() {
        return items.iterator();
    }

    public boolean emailAddressesRequested() {
        return items.contains(EMAIL_ADDRESSES);
    }

    public boolean phoneNumbersRequested() {
        return items.contains(PHONE_NUMBERS);
    }

    public Stream<String> stream() {
        return items.stream();
    }

    public static Collection<String> allItems() {
        return Arrays.asList(
                EMAIL_ADDRESSES,
                PHONE_NUMBERS
        );
    }

}
