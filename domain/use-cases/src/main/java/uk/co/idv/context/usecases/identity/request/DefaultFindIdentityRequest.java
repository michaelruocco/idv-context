package uk.co.idv.context.usecases.identity.request;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

@Builder
@Data
public class DefaultFindIdentityRequest implements FindIdentityRequest {

    private final Aliases aliases;

}
