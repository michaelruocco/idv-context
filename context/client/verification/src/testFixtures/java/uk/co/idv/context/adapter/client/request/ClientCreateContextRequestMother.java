package uk.co.idv.context.adapter.client.request;

import uk.co.idv.context.adapter.client.header.ContextRequestHeadersMother;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;

public interface ClientCreateContextRequestMother {

    static ClientCreateContextRequest build() {
        return ClientCreateContextRequest.builder()
                .body(FacadeCreateContextRequestMother.build())
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
