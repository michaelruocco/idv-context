package uk.co.idv.method.entities.method.fake;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.With;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodConfig;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.Results;

@Builder(toBuilder = true)
@Data
public class FakeMethod implements Method {

    @With
    private final String name;
    private final Eligibility eligibility;
    private final MethodConfig config;

    @Getter(AccessLevel.NONE)
    private final Boolean overrideComplete;
    @Getter(AccessLevel.NONE)
    private final Boolean overrideSuccessful;

    @Builder.Default
    private final Results results = new Results();

    @Override
    public boolean isComplete() {
        if (overrideComplete != null) {
            return overrideComplete;
        }
        return isSuccessful() || !hasAttemptsRemaining();
    }

    @Override
    public boolean isSuccessful() {
        if (overrideSuccessful != null) {
            return overrideSuccessful;
        }
        return results.containsSuccessful();
    }

    private boolean hasAttemptsRemaining() {
        return results.size() < config.getMaxNumberOfAttempts();
    }

    @Override
    public FakeMethod add(Result result) {
        return toBuilder()
                .results(results.add(result))
                .build();
    }

}
