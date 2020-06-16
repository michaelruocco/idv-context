package uk.co.idv.context.usecases.identity.find;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;

@Builder
@Data
public class DefaultFindIdentityRequest implements FindIdentityRequest {

    private final Aliases aliases;

}
