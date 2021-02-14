package uk.co.idv.context.adapter.client.request;

import uk.co.idv.context.adapter.client.header.ContextRequestHeadersMother;
import uk.co.idv.context.entities.verification.CompleteVerificationRequestMother;

public interface ClientCompleteVerificationRequestMother {

    static ClientCompleteVerificationRequest build() {
        return ClientCompleteVerificationRequest.builder()
                .body(CompleteVerificationRequestMother.successful())
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
