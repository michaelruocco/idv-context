package uk.co.idv.context.entities.policy;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FakePolicy implements Policy {

    private final PolicyKey key;

    @Override
    public UUID getId() {
        return key.getId();
    }

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return key.appliesTo(request);
    }

    @Override
    public int getPriority() {
        return key.getPriority();
    }

    @Override
    public PolicyKey getKey() {
        return key;
    }

}
