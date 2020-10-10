package uk.co.idv.context.entities.result;

import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;

public interface ServiceRecordResultResponseMother {

    static ServiceRecordResultResponse build() {
        return builder().build();
    }

    static ServiceRecordResultResponse.ServiceRecordResultResponseBuilder builder() {
        Result result = ResultMother.build();
        return ServiceRecordResultResponse.builder()
                .original(ContextMother.build())
                .updated(ContextMother.withMethod(FakeMethodMother.withResult(result)));
    }

}
