package uk.co.idv.config.manual;

import lombok.Builder;
import uk.co.idv.context.adapter.identity.service.find.StubFindIdentity;
import uk.co.idv.context.adapter.identity.service.find.StubFindIdentityConfig;
import uk.co.idv.context.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.service.find.FindIdentity;
import uk.co.idv.context.usecases.identity.service.find.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.service.update.DefaultUpdateIdentity;
import uk.co.idv.context.usecases.identity.service.update.ExternalUpdateIdentity;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentity;

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
