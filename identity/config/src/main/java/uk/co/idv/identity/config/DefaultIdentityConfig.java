package uk.co.idv.identity.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.json.IdentityErrorHandler;
import uk.co.idv.identity.adapter.protect.mask.channel.ChannelMasker;
import uk.co.idv.identity.adapter.protect.mask.identity.IdentityMasker;
import uk.co.idv.identity.entities.alias.DefaultAliasFactory;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
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
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.util.Collections;
import java.util.function.UnaryOperator;

@Builder
@Slf4j
public class DefaultIdentityConfig implements IdentityConfig {

    private final CompositeMergeIdentitiesHandler mergeHandler = new CompositeMergeIdentitiesHandler();
    private final ExternalFindIdentityConfig externalFindIdentityConfig;
    private final UuidGenerator uuidGenerator;
    private final IdentityRepository repository;

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
    public IdentityErrorHandler errorHandler() {
        return new IdentityErrorHandler();
    }

    @Override
    public UnaryOperator<Channel> channelProtector() {
        return new ChannelMasker();
    }

    @Override
    public UnaryOperator<Identity> identityProtector() {
        return new IdentityMasker();
    }

    @Override
    public void addMergeIdentitiesHandler(MergeIdentitiesHandler handler) {
        mergeHandler.addHandler(handler);
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
                .handler(mergeHandler)
                .repository(repository)
                .build();
    }

    private IdvIdAllocator idvIdAllocator() {
        return new IdvIdAllocator(uuidGenerator);
    }

}
