package uk.co.idv.identity.entities.identity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@Data
@RequiredArgsConstructor
public class RequestedData implements Iterable<String> {

    public static final String EMAIL_ADDRESSES = "email-addresses";
    public static final String PHONE_NUMBERS = "phone-numbers";

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
        return items.contains(EMAIL_ADDRESSES);
    }

    public boolean phoneNumbersRequested() {
        return  items.contains(PHONE_NUMBERS);
    }

    public static Collection<String> allItems() {
        return Arrays.asList(
                EMAIL_ADDRESSES,
                PHONE_NUMBERS
        );
    }

}
