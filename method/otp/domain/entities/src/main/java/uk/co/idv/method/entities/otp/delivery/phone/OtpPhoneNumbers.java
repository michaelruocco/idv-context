package uk.co.idv.method.entities.otp.delivery.phone;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class OtpPhoneNumbers implements Iterable<OtpPhoneNumber> {

    @Getter(AccessLevel.NONE)
    private final Collection<OtpPhoneNumber> values;

    public OtpPhoneNumbers(OtpPhoneNumber... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<OtpPhoneNumber> iterator() {
        return values.iterator();
    }

    public Stream<OtpPhoneNumber> stream() {
        return values.stream();
    }

}
