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

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private final Collection<String> items;

    public RequestedData(String... items) {
        this(Arrays.asList(items));
    }

    @Override
    public Iterator<String> iterator() {
        return items.iterator();
    }

    public boolean emailAddressesRequested() {
        return items.contains(RequestedDataItems.EMAIL_ADDRESSES);
    }

    public boolean phoneNumbersRequested() {
        return items.contains(RequestedDataItems.PHONE_NUMBERS);
    }

    public Stream<String> stream() {
        return items.stream();
    }

}
