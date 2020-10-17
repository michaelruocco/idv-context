package uk.co.idv.identity.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
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
import uk.co.idv.identity.usecases.identity.create.CreateIdentity;
import uk.co.idv.identity.usecases.identity.create.IdentityValidator;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;
import uk.co.idv.identity.usecases.identity.idvid.IdvIdAllocator;
import uk.co.idv.identity.usecases.identity.merge.CompositeMergeIdentitiesHandler;
import uk.co.idv.identity.usecases.identity.merge.MergeIdentities;
import uk.co.idv.identity.usecases.identity.merge.MergeIdentitiesHandler;
import uk.co.idv.identity.usecases.identity.save.DefaultSaveIdentity;
import uk.co.idv.identity.usecases.identity.save.SaveIdentityStrategy;
import uk.co.idv.identity.usecases.identity.save.external.ExternalSaveIdentityStrategy;
import uk.co.idv.identity.usecases.identity.save.internal.InternalSaveIdentityStrategy;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

import java.util.Collection;
import java.util.Collections;

@Builder
@Slf4j
public class DefaultIdentityConfig implements IdentityConfig {

    private final Collection<MergeIdentitiesHandler> mergeHandlers;
    private final ExternalFindIdentityConfig externalFindIdentityConfig;
    private final IdGenerator idGenerator;
    private final IdentityRepository repository;

    public static IdentityConfig build(ExternalFindIdentityConfig externalFindIdentityConfig) {
        return DefaultIdentityConfig.builder()
                .externalFindIdentityConfig(externalFindIdentityConfig)
                .idGenerator(new RandomIdGenerator())
                .repository(new InMemoryIdentityRepository())
                .mergeHandlers(Collections.emptyList())
                .build();
    }

    @Override
    public FindIdentity findIdentity() {
        return new FindIdentity(repository);
    }

    @Override
    public CreateEligibility createEligibility() {
        return new CompositeCreateEligibility(
                abcCreateEligibility(),
                defaultCreateEligibility()
        );
    }

    @Override
    public IdentityService identityService() {
        return IdentityService.builder()
                .update(updateIdentity(new InternalSaveIdentityStrategy()))
                .find(new FindIdentity(repository))
                .aliasFactory(new DefaultAliasFactory())
                .build();
    }

    @Override
    public AliasFactory aliasFactory() {
        return new DefaultAliasFactory();
    }

    @Override
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
                .find(externalFindIdentityConfig.externalFindIdentity())
                .update(updateIdentity(new ExternalSaveIdentityStrategy()))
                .build();
    }

    private CreateEligibility internalCreateEligibility() {
        return InternalCreateEligibility.builder()
                .find(new FindIdentity(repository))
                .build();
    }

    private UpdateIdentity updateIdentity(SaveIdentityStrategy saveStrategy) {
        return UpdateIdentity.builder()
                .create(createIdentity())
                .merge(mergeIdentities())
                .save(new DefaultSaveIdentity(repository, saveStrategy))
                .repository(repository)
                .build();
    }

    private CreateIdentity createIdentity() {
        return CreateIdentity.builder()
                .idvIdAllocator(idvIdAllocator())
                .validator(new IdentityValidator())
                .repository(repository)
                .build();
    }

    private MergeIdentities mergeIdentities() {
        return MergeIdentities.builder()
                .idvIdAllocator(idvIdAllocator())
                .handler(new CompositeMergeIdentitiesHandler(mergeHandlers))
                .repository(repository)
                .build();
    }

    private IdvIdAllocator idvIdAllocator() {
        return new IdvIdAllocator(idGenerator);
    }

}
