package uk.co.idv.context.entities.policy.sequence;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.policy.RequestedDataProvider;

@Builder
@Data
public class SequencePolicy implements RequestedDataProvider {

    private final String name;
    private final StagePolicies stagePolicies;

    @Override
    public RequestedData getRequestedData() {
        return stagePolicies.getRequestedData();
    }

}
