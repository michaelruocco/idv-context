package uk.co.idv.policy.entities.policy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class FakePolicy implements Policy {

    private final PolicyKey key;

    @Override
    public UUID getId() {
        return key.getId();
    }

    @Override
    public int getPriority() {
        return key.getPriority();
    }

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return key.appliesTo(request);
    }

}
