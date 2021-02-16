package uk.co.idv.context.entities.context.create;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;

@Builder
@Data
public class FacadeCreateContextRequest implements CreateContextRequest {

    @With
    private final Channel channel;
    private final Aliases aliases;
    private final Activity activity;

}
