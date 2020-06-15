package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.usecases.identity.service.ExternalIdentityService;
import uk.co.idv.context.usecases.identity.service.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.service.find.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.service.merge.MergeIdentities;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentity;

@Builder
public class IdentityServiceFactory {

    private final ExternalFindSupplier externalFindSupplier;
    private final IdentityRepository repository;

    public IdentityService build() {
        return ExternalIdentityService.builder()
                .externalFind(externalFindSupplier.get())
                .internalFind(new InternalFindIdentity(repository))
                .update(updateIdentity())
                .build();
    }

    private UpdateIdentity updateIdentity() {
        return UpdateIdentity.builder()
                .repository(repository)
                .create(createIdentity())
                .merge(new MergeIdentities())
                .build();
    }

    private CreateIdentity createIdentity() {
        return CreateIdentity.builder()
                .repository(repository)
                .build();
    }

}
