package uk.co.idv.config.manual;

import lombok.Builder;
import uk.co.idv.context.adapter.identity.find.external.StubFindIdentity;
import uk.co.idv.context.adapter.identity.find.external.StubFindIdentityConfig;
import uk.co.idv.context.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.find.internal.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.update.DefaultUpdateIdentity;
import uk.co.idv.context.usecases.identity.update.external.ExternalUpdateIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class ManualConfig {

    @Builder.Default private final IdentityRepository repository = new InMemoryIdentityRepository();

    private final StubFindIdentityConfig stubConfig;

    public UpdateIdentity updateIdentity() {
        return ExternalUpdateIdentity.builder()
                .externalFind(StubFindIdentity.build(stubConfig))
                .update(DefaultUpdateIdentity.build(repository))
                .build();
    }

    public FindIdentity findIdentity() {
        return new InternalFindIdentity(repository);
    }

}
