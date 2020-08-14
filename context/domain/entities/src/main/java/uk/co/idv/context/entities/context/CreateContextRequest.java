package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.channel.Channel;

@Builder
@Data
public class CreateContextRequest {

    private final Channel channel;
    private final Alias alias;
    private final Activity activity;

}
