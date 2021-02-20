package uk.co.idv.context.adapter.verification.client.request;

import uk.co.idv.context.adapter.verification.client.header.ContextRequestHeadersMother;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;

public interface ClientCompleteVerificationRequestMother {

    static ClientCompleteVerificationRequest build() {
        return ClientCompleteVerificationRequest.builder()
                .body(CompleteVerificationRequestMother.successful())
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
