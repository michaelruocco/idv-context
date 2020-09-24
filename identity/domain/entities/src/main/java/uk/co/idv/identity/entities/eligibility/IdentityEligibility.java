package uk.co.idv.identity.entities.eligibility;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.RequestedData;


@Builder
@Data
public class IdentityEligibility {

    private final Channel channel;
    private final Aliases aliases;
    private final RequestedData requestedData;
    private final Identity identity;

}
