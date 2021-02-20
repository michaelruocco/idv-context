package uk.co.idv.context.adapter.verification.client.request;

import uk.co.idv.context.adapter.verification.client.header.ContextRequestHeadersMother;
import uk.co.idv.method.entities.verification.CreateVerificationRequestMother;

public interface ClientCreateVerificationRequestMother {

    static ClientCreateVerificationRequest build() {
        return ClientCreateVerificationRequest.builder()
                .body(CreateVerificationRequestMother.build())
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
