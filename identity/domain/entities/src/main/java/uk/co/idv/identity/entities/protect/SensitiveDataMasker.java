package uk.co.idv.identity.entities.protect;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMasker;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMasker;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SensitiveDataMasker implements SensitiveDataProtector {

    private final Map<Class<?>, UnaryOperator<String>> maskers;

    public SensitiveDataMasker() {
        this(buildDefaultMaskers());
    }

    public SensitiveDataMasker(Collection<TypedProtector<?>> maskers) {
        this(toMap(maskers));
    }

    @Override
    public Identity protect(Identity identity) {
        return identity
                .withEmailAddresses(protect(identity.getEmailAddresses()))
                .withPhoneNumbers(protect(identity.getPhoneNumbers()));
    }

    @Override
    public EmailAddresses protect(EmailAddresses emailAddresses) {
        return new EmailAddresses(emailAddresses.stream()
                .map(this::protect)
                .collect(Collectors.toList()));
    }

    @Override
    public PhoneNumbers protect(PhoneNumbers phoneNumbers) {
        return new PhoneNumbers(phoneNumbers.stream()
                .map(this::protect)
                .collect(Collectors.toList()));
    }

    public <T> T protect(Updatable<T> value) {
        UnaryOperator<String> mask = findMasker(value.getClass());
        return value.withValue(mask.apply(value.getValue()));
    }

    private UnaryOperator<String> findMasker(Class<?> type) {
        return Optional.ofNullable(maskers.get(type))
                .orElseThrow(() -> new MaskingNotSupportedException(type));
    }

    private static Collection<TypedProtector<?>> buildDefaultMaskers() {
        return Arrays.asList(
                new TypedProtector<>(PhoneNumber.class, new PhoneNumberMasker()),
                new TypedProtector<>(EmailAddress.class, new EmailAddressMasker())
        );
    }

    private static Map<Class<?>, UnaryOperator<String>> toMap(Collection<TypedProtector<?>> maskers) {
        return maskers.stream().collect(Collectors.toMap(TypedProtector::getType, Function.identity()));
    }

}
