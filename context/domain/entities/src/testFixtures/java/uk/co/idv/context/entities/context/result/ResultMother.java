package uk.co.idv.context.entities.context.result;

import java.time.Instant;
import java.util.UUID;

public interface ResultMother {

    static Result build() {
        return builder().build();
    }

    static Result successful() {
        return build();
    }

    static Result unsuccessful() {
        return builder().successful(false).build();
    }

    static Result withMethodName(String name) {
        return builder().methodName(name).build();
    }

    static Result.ResultBuilder builder() {
        return Result.builder()
                .methodName("method-name")
                .successful(true)
                .timestamp(Instant.parse("2020-10-01T19:10:22.000Z"))
                .verificationId(UUID.fromString("be08a961-8e0d-431d-ad44-4d8d872d1f4a"));
    }

}
