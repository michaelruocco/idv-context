package uk.co.idv.method.entities.method.fake.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.method.MethodConfig;
import uk.co.idv.method.entities.policy.MethodPolicy;

@Builder
@Data
public class FakeMethodPolicy implements MethodPolicy {

    private static final RequestedData EMPTY_REQUESTED_DATA = new RequestedData();

    private final String name;
    private final MethodConfig config;

    @Override
    public RequestedData getRequestedData() {
        return EMPTY_REQUESTED_DATA;
    }

}
