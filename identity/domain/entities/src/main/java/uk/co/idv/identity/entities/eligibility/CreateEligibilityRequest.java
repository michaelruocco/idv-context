package uk.co.idv.identity.entities.eligibility;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.RequestedData;

@Builder
@Data
public class CreateEligibilityRequest implements FindIdentityRequest {

    private final Aliases aliases;
    private final Channel channel;
    private final RequestedData requestedData;

}
