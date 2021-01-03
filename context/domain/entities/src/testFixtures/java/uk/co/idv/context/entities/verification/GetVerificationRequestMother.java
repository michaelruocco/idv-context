package uk.co.idv.context.entities.verification;


import java.util.UUID;

public interface GetVerificationRequestMother {

    static GetVerificationRequest build() {
        return builder().build();
    }

    static GetVerificationRequest.GetVerificationRequestBuilder builder() {
        return GetVerificationRequest.builder()
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .verificationId(UUID.fromString("81e11840-140e-45ac-a6af-40aa653a146e"));
    }

}
