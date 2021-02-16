package uk.co.idv.method.entities.verification;


import java.util.UUID;

public interface CreateVerificationRequestMother {

    static CreateVerificationRequest build() {
        return builder().build();
    }

    static CreateVerificationRequest withContextId(UUID contextId) {
        return builder().contextId(contextId).build();
    }

    static CreateVerificationRequest.CreateVerificationRequestBuilder builder() {
        return CreateVerificationRequest.builder()
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .methodName("fake-method");
    }

}
