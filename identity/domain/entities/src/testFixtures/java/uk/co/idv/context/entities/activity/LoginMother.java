package uk.co.idv.context.entities.activity;

import java.time.Instant;

public interface LoginMother {

    static Login withTimestamp(Instant timestamp) {
        return new Login(timestamp);
    }

    static Login login() {
        return withTimestamp(Instant.parse("2020-06-06T12:36:15.179Z"));
    }

}
