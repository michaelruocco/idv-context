package uk.co.idv.identity.entities.phonenumber;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberTest {

    @Test
    void shouldReturnNumberValue() {
        String value = "+447089123456";

        PhoneNumber number = PhoneNumberMother.withNumber(value);

        assertThat(number.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnLastUpdatedIfSet() {
        Instant lastUpdated = Instant.now();

        PhoneNumber number = PhoneNumberMother.withLastUpdated(lastUpdated);

        assertThat(number.getLastUpdated()).contains(lastUpdated);
    }

    @Test
    void shouldReturnEmptyIfLastUpdatedNotSet() {
        PhoneNumber number = PhoneNumberMother.withoutLastUpdated();

        assertThat(number.getLastUpdated()).isEmpty();
    }

}
