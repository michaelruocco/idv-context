package uk.co.idv.method.entities.fake;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodConfig;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.Results;

@Builder(toBuilder = true)
@Data
public class FakeMethod implements Method {

    private final String name;
    private final Eligibility eligibility;
    private final MethodConfig config;
    private final boolean complete;
    private final boolean successful;

    @Builder.Default
    private final Results results = new Results();

    @Override
    public FakeMethod add(Result result) {
        return toBuilder()
                .results(results.add(result))
                .build();
    }

}
