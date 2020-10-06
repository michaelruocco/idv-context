package uk.co.idv.identity.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.eligibility.external.StubExternalFindIdentity;
import uk.co.idv.identity.adapter.json.IdentityErrorHandler;
import uk.co.idv.identity.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.DefaultAliasFactory;
import uk.co.idv.identity.usecases.eligibility.ChannelCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.CompositeCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.eligibility.DefaultCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.SupportedCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.external.ExternalCreateEligibility;
import uk.co.idv.identity.usecases.eligibility.external.ExternalFindIdentity;
import uk.co.idv.identity.usecases.eligibility.internal.InternalCreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

import java.util.Collections;

@Builder
@Slf4j
public class IdentityConfig {

    @Builder.Default
    private final IdentityRepository repository = new InMemoryIdentityRepository();

    private final ExternalFindIdentity externalFindIdentity = StubExternalFindIdentity.withExampleConfig();

    public FindIdentity findIdentity() {
        return new FindIdentity(repository);
    }

    public CreateEligibility createEligibility() {
        return new CompositeCreateEligibility(
                abcCreateEligibility(),
                defaultCreateEligibility()
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

    private SupportedCreateEligibility defaultCreateEligibility() {
        return new DefaultCreateEligibility(internalCreateEligibility());
    }

    private SupportedCreateEligibility abcCreateEligibility() {
        return ChannelCreateEligibility.builder()
                .supportedChannelIds(Collections.singleton("abc"))
                .create(externalCreateEligibility())
                .build();
    }

    private ExternalCreateEligibility externalCreateEligibility() {
        return ExternalCreateEligibility.builder()
                .find(externalFindIdentity)
                .update(UpdateIdentity.buildExternal(repository))
                .build();
    }

    private CreateEligibility internalCreateEligibility() {
        return InternalCreateEligibility.build(repository);
    }

}
