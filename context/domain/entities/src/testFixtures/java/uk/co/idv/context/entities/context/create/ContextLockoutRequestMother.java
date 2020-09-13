package uk.co.idv.context.entities.context.create;

import java.time.Instant;

public interface ContextLockoutRequestMother {

    static ContextLockoutRequest build() {
        return builder().build();
    }

    static ContextLockoutRequest.ContextLockoutRequestBuilder builder() {
        return ContextLockoutRequest.builder()
                .contextRequest(DefaultCreateContextRequestMother.build())
                .timestamp(Instant.parse("2020-08-28T06:27:58.977Z"));
    }

}
