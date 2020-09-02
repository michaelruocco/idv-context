package uk.co.idv.context.config.identity;

import lombok.Builder;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStub;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;
import uk.co.idv.context.adapter.json.error.handler.IdentityErrorHandler;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.DefaultAliasFactory;
import uk.co.idv.identity.usecases.eligibility.ChannelCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.CompositeCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.eligibility.external.ExternalCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.internal.InternalCreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

import java.util.Collections;

@Builder
public class IdentityConfig implements FindIdentityProvider {

    private final IdentityRepository repository;
    private final ExternalFindIdentityStubConfig stubConfig;

    @Override
    public FindIdentity provideFindIdentity() {
        return new FindIdentity(repository);
    }

    public CreateEligibility createEligibility() {
        return new CompositeCreateEligibility(
                rsaCreateEligibility(),
                as3CreateEligibility()
        );
    }

    public IdentityService identityService() {
        return IdentityService.build(repository);
    }

    public AliasFactory aliasFactory() {
        return new DefaultAliasFactory();
    }

    public IdentityErrorHandler errorHandler() {
        return new IdentityErrorHandler();
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
                .create(externalCreateEligibility())
                .build();
    }

    private ExternalCreateEligibility externalCreateEligibility() {
        return ExternalCreateEligibility.builder()
                .find(ExternalFindIdentityStub.build(stubConfig))
                .update(UpdateIdentity.buildExternal(repository))
                .build();
    }

    private CreateEligibility internalCreateEligibility() {
        return InternalCreateEligibility.build(repository);
    }

}
