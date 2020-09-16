package uk.co.idv.context.entities.context.method;

import lombok.Builder;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.MethodConfig;

import static org.mockito.Mockito.mock;

@Builder
public class FakeMethod implements Method {

    private final Eligibility eligibility;

    @Override
    public String getName() {
        return "fake-method";
    }

    @Override
    public Eligibility getEligibility() {
        return eligibility;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public MethodConfig getConfig() {
        return mock(MethodConfig.class);
    }

}
