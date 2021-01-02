package uk.co.idv.identity.adapter.protect.mask.phonenumber;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PhoneNumbersMasker implements UnaryOperator<PhoneNumbers> {

    private final UnaryOperator<String> stringMasker;

    public PhoneNumbersMasker() {
        this(new PhoneNumberStringMasker());
    }

    @Override
    public PhoneNumbers apply(PhoneNumbers phoneNumbers) {
        return new PhoneNumbers(phoneNumbers.stream()
                .map(this::protect)
                .collect(Collectors.toList()));
    }

    public PhoneNumber protect(PhoneNumber number) {
        return number.withValue(stringMasker.apply(number.getValue()));
    }

}
