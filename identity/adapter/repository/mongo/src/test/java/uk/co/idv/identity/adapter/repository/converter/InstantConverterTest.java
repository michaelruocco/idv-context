package uk.co.idv.identity.adapter.repository.converter;

import org.bson.BsonTimestamp;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class InstantConverterTest {

    private final InstantConverter converter = new InstantConverter();

    @Test
    void shouldConvertInstantToBsonTimestamp() {
        Instant instant = Instant.now();

        BsonTimestamp timestamp = converter.toTimestamp(instant);

        assertThat(timestamp.getValue()).isEqualTo(instant.toEpochMilli());
    }

    @Test
    void shouldConvertBsonTimestampToInstant() {
        Instant expectedInstant = Instant.now();
        BsonTimestamp timestamp = new BsonTimestamp(expectedInstant.toEpochMilli());

        Instant instant = converter.toInstant(timestamp);

        assertThat(instant).isEqualTo(expectedInstant.truncatedTo(ChronoUnit.MILLIS));
    }

}
