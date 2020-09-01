package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OtpPhoneNumbers implements Iterable<OtpPhoneNumber> {

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
