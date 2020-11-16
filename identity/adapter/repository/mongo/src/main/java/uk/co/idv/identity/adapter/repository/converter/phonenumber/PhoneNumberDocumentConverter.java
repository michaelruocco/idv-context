package uk.co.idv.identity.adapter.repository.converter.phonenumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.converter.InstantConverter;
import uk.co.idv.identity.adapter.repository.type.PhoneNumberDocument;
import uk.co.idv.identity.adapter.repository.type.PhoneNumberDocument.PhoneNumberDocumentBuilder;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber.PhoneNumberBuilder;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class PhoneNumberDocumentConverter {

    private final InstantConverter instantConverter;

    public PhoneNumberDocumentConverter() {
        this(new InstantConverter());
    }

    public PhoneNumber toPhoneNumber(PhoneNumberDocument document) {
        PhoneNumberBuilder builder = PhoneNumber.builder()
                .value(document.getValue());
        Optional.ofNullable(document.getLastUpdated())
                .map(instantConverter::toInstant)
                .ifPresent(builder::lastUpdated);
        return builder.build();
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
