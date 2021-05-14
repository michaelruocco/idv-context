package uk.co.idv.identity.adapter.repository.converter.phonenumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.converter.InstantConverter;
import uk.co.idv.identity.adapter.repository.document.PhoneNumberDocument;
import uk.co.idv.identity.adapter.repository.document.PhoneNumberDocument.PhoneNumberDocumentBuilder;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber.PhoneNumberBuilder;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static uk.co.idv.identity.adapter.repository.converter.NullCollectionToEmptyList.toEmptyListIfNull;

@RequiredArgsConstructor
@Slf4j
public class PhoneNumberDocumentConverter {

    private final InstantConverter instantConverter;

    public PhoneNumberDocumentConverter() {
        this(new InstantConverter());
    }

    public PhoneNumbers toPhoneNumbers(Collection<PhoneNumberDocument> documents) {
        Collection<PhoneNumber> numbers = toEmptyListIfNull(documents).stream()
                .map(this::toPhoneNumber)
                .collect(Collectors.toList());
        return new PhoneNumbers(numbers);
    }

    public PhoneNumber toPhoneNumber(PhoneNumberDocument document) {
        PhoneNumberBuilder builder = PhoneNumber.builder()
                .value(document.getValue());
        Optional.ofNullable(document.getLastUpdated())
                .map(instantConverter::toInstant)
                .ifPresent(builder::lastUpdated);
        return builder.build();
    }

    public Collection<PhoneNumberDocument> toDocuments(PhoneNumbers phoneNumbers) {
        return phoneNumbers.stream().map(this::toDocument).collect(Collectors.toList());
    }

    public PhoneNumberDocument toDocument(PhoneNumber number) {
        PhoneNumberDocumentBuilder builder = PhoneNumberDocument.builder()
                .value(number.getValue());
        number.getLastUpdated()
                .map(instantConverter::toTimestamp)
                .ifPresent(builder::lastUpdated);
        return builder.build();
    }

}
