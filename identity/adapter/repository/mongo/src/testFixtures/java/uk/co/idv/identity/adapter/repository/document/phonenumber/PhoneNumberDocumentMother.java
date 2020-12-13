package uk.co.idv.identity.adapter.repository.document.phonenumber;

import org.bson.BsonTimestamp;
import uk.co.idv.identity.adapter.repository.document.PhoneNumberDocument;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

public class PhoneNumberDocumentMother {

    private PhoneNumberDocumentMother() {
        // utility class
    }

    public static Collection<PhoneNumberDocument> two() {
        return Arrays.asList(example(), example1());
    }

    public static PhoneNumberDocument example() {
        return builder().build();
    }

    static PhoneNumberDocument example1() {
        return builder()
                .value("+447089121212")
                .lastUpdated(null)
                .build();
    }

    public static PhoneNumberDocument withoutLastUpdated() {
        return builder().lastUpdated(null).build();
    }

    private static PhoneNumberDocument.PhoneNumberDocumentBuilder builder() {
        return PhoneNumberDocument.builder()
                .value("+447089111111")
                .lastUpdated(new BsonTimestamp(Instant.parse("2020-08-29T20:55:12.825Z").toEpochMilli()));
    }

}
