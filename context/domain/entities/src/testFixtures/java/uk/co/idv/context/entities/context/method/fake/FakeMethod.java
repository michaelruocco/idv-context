package uk.co.idv.context.entities.context.method.fake;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.policy.method.MethodConfig;

@Builder
@Data
public class FakeMethod implements Method {

    private final String name;
    private final Eligibility eligibility;
    private final MethodConfig config;
    private final boolean complete;
    private final boolean successful;

}
