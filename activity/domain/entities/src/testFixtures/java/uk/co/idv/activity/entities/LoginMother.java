package uk.co.idv.activity.entities;

import java.time.Instant;

public interface LoginMother {

    static Login withTimestamp(Instant timestamp) {
        return builder().timestamp(timestamp).build();
    }

    static Login login() {
        return withTimestamp(Instant.parse("2020-06-06T12:36:15.179Z"));
    }

    static Login.LoginBuilder builder() {
        return Login.builder()
                .timestamp(Instant.parse("2020-06-06T12:36:15.179Z"))
                .system("example-system");
    }
}
