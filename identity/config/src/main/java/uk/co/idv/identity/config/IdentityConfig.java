package uk.co.idv.identity.config;

import lombok.Builder;
import uk.co.idv.identity.adapter.eligibility.external.ExternalFindIdentityStub;
import uk.co.idv.identity.adapter.eligibility.external.ExternalFindIdentityStubConfig;
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
import uk.co.idv.identity.usecases.eligibility.internal.InternalCreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Executors;

@Builder
public class IdentityConfig {

    @Builder.Default
    private final IdentityRepository repository = new InMemoryIdentityRepository();

    @Builder.Default
    private final ExternalFindIdentityStubConfig stubConfig = buildDefaultStubConfig();

    public FindIdentity findIdentity() {
        return new FindIdentity(repository);
    }

    public CreateEligibility createEligibility() {
        return new CompositeCreateEligibility(
                as3CreateEligibility(),
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

    private SupportedCreateEligibility as3CreateEligibility() {
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

    private static ExternalFindIdentityStubConfig buildDefaultStubConfig() {
        return ExternalFindIdentityStubConfig.builder()
                .executor(Executors.newFixedThreadPool(2))
                .timeout(Duration.ofMillis(250))
                .phoneNumberDelay(Duration.ofMillis(400))
                .emailAddressDelay(Duration.ofMillis(100))
                .build();
    }

}
