package uk.co.idv.context.adapter.client.request;

import uk.co.idv.context.adapter.client.headers.ContextRequestHeadersMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;

public interface ClientRecordContextResultRequestMother {

    static ClientRecordContextResultRequest build() {
        return ClientRecordContextResultRequest.builder()
                .body(FacadeRecordResultRequestMother.build())
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
