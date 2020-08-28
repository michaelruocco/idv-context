package uk.co.idv.context.usecases.eligibility;

import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.FindIdentityRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CompositeCreateEligibility implements CreateEligibility {

    private final Map<String, CreateEligibility> creates = new HashMap<>();

    public CompositeCreateEligibility(ChannelCreateEligibility... creates) {
        this(Arrays.asList(creates));
    }

    public CompositeCreateEligibility(Collection<ChannelCreateEligibility> creates) {
        creates.forEach(this::add);
    }

    @Override
    public Eligibility create(FindIdentityRequest request) {
        String channelId = request.getChannelId();
        return selectCreate(channelId)
                .map(create -> create.create(request))
                .orElseThrow(() -> new EligibilityNotConfiguredException(channelId));
    }

    private void add(ChannelCreateEligibility create) {
        create.getSupportedChannelIds()
                .forEach(channelId -> creates.put(channelId, create));
    }

    private Optional<CreateEligibility> selectCreate(String channelId) {
        return Optional.ofNullable(creates.get(channelId));
    }

}
