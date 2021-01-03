package uk.co.idv.context.adapter.client.request;

import uk.co.idv.context.adapter.client.headers.ContextRequestHeadersMother;
import uk.co.idv.context.entities.verification.CreateVerificationRequestMother;

public interface ClientCreateVerificationRequestMother {

    static ClientCreateVerificationRequest build() {
        return ClientCreateVerificationRequest.builder()
                .body(CreateVerificationRequestMother.build())
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
