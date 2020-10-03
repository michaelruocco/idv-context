package uk.co.idv.context.entities.context.method.fake;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.result.Result;
import uk.co.idv.context.entities.context.result.Results;
import uk.co.idv.context.entities.policy.method.MethodConfig;

@Builder(toBuilder = true)
@Data
public class FakeMethod implements Method {

    private final String name;
    private final Eligibility eligibility;
    private final MethodConfig config;
    private final boolean complete;
    private final boolean successful;
    private final Results results;

    @Override
    public FakeMethod add(Result result) {
        return toBuilder()
                .results(results.add(result))
                .build();
    }

}
