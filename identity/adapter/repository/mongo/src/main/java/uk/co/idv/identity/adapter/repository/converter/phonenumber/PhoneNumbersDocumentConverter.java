package uk.co.idv.identity.adapter.repository.converter.phonenumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.type.PhoneNumberDocument;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class PhoneNumbersDocumentConverter {

    private final PhoneNumberDocumentConverter documentConverter;

    public PhoneNumbersDocumentConverter() {
        this(new PhoneNumberDocumentConverter());
    }

    public PhoneNumbers toPhoneNumbers(Collection<PhoneNumberDocument> documents) {
        Collection<PhoneNumber> numbers = documents.stream()
                .map(this::toPhoneNumber)
                .collect(Collectors.toList());
        return new PhoneNumbers(numbers);
    }

    public PhoneNumber toPhoneNumber(PhoneNumberDocument document) {
        return documentConverter.toPhoneNumber(document);
    }

    public Collection<PhoneNumberDocument> toDocuments(PhoneNumbers phoneNumbers) {
        return phoneNumbers.stream().map(this::toDocument).collect(Collectors.toList());
    }

    public PhoneNumberDocument toDocument(PhoneNumber phoneNumber) {
        return documentConverter.toDocument(phoneNumber);
    }

}
