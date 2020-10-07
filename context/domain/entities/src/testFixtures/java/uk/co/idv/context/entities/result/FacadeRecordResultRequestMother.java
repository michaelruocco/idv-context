package uk.co.idv.context.entities.result;

import uk.co.idv.method.entities.result.ResultMother;

import java.util.UUID;

public interface FacadeRecordResultRequestMother {

    static FacadeRecordResultRequest build() {
        return builder().build();
    }

    static FacadeRecordResultRequest.FacadeRecordResultRequestBuilder builder() {
        return FacadeRecordResultRequest.builder()
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .result(ResultMother.successful());
    }

}
