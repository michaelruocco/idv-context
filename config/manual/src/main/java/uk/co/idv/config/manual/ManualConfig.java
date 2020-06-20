package uk.co.idv.config.manual;

import lombok.Builder;
import uk.co.idv.context.adapter.eligibility.external.StubFindIdentity;
import uk.co.idv.context.adapter.eligibility.external.StubFindIdentityConfig;
import uk.co.idv.context.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.context.usecases.eligibility.ChannelCreateEligibility;
import uk.co.idv.context.usecases.eligibility.CompositeCreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.external.ExternalCreateEligibility;
import uk.co.idv.context.usecases.eligibility.internal.InternalCreateEligibility;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import java.util.Collections;

@Builder
public class ManualConfig {

    @Builder.Default
    private final IdentityRepository repository = new InMemoryIdentityRepository();

    private final StubFindIdentityConfig stubConfig;

    public CreateEligibility createEligibility() {
        return new CompositeCreateEligibility(
                rsaCreateEligibility(),
                as3CreateEligibility()
        );
    }

    public FindIdentity findIdentity() {
        return new FindIdentity(repository);
    }

    public UpdateIdentity updateIdentity() {
        return UpdateIdentity.build(repository);
    }

    private ChannelCreateEligibility rsaCreateEligibility() {
        return ChannelCreateEligibility.builder()
                .supportedChannelIds(Collections.singleton("gb-rsa"))
                .create(internalCreateEligibility())
                .build();
    }

    private ChannelCreateEligibility as3CreateEligibility() {
        return ChannelCreateEligibility.builder()
                .supportedChannelIds(Collections.singleton("as3"))
                .create(externalCreateEligibility(StubFindIdentity.build(stubConfig)))
                .build();
    }

    private ExternalCreateEligibility externalCreateEligibility(ExternalFindIdentity find) {
        return new ExternalCreateEligibility(find, updateIdentity());
    }

    private InternalCreateEligibility internalCreateEligibility() {
        return new InternalCreateEligibility(repository);
    }

}
