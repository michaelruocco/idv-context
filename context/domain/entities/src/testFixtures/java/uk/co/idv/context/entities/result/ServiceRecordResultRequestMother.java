package uk.co.idv.context.entities.result;

import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.method.entities.result.ResultMother;

public interface ServiceRecordResultRequestMother {

    static ServiceRecordResultRequest build() {
        return builder().build();
    }

    static ServiceRecordResultRequest.ServiceRecordResultRequestBuilder builder() {
        return ServiceRecordResultRequest.builder()
                .context(ContextMother.build())
                .result(ResultMother.successful());
    }

}
