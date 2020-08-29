package uk.co.idv.context.entities.phonenumber;

import uk.co.idv.context.entities.phonenumber.PhoneNumber.PhoneNumberBuilder;

import java.time.Instant;

public interface PhoneNumberMother {

    static PhoneNumber example() {
        return withNumber("+447089111111");
    }

    static PhoneNumber example1() {
        return builder()
                .value("+447089121212")
                .lastUpdated(null)
                .build();
    }

    static PhoneNumber withoutLastUpdated() {
        return withLastUpdated(null);
    }

    static PhoneNumber withLastUpdated(Instant lastUpdated) {
        return builder().lastUpdated(lastUpdated).build();
    }

    static PhoneNumber withNumber(String number) {
        return builder().value(number).build();
    }

    static PhoneNumberBuilder builder() {
        return PhoneNumber.builder()
                .value("+447089111111")
                .lastUpdated(Instant.parse("2020-08-29T20:55:12.825Z"));
    }

}
