package uk.co.idv.context.entities.eligibility;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;

@Builder
@Data
public class Eligibility {

    private final Channel channel;
    private final Aliases aliases;
    private final Collection<String> requested;
    private final Identity identity;

}
