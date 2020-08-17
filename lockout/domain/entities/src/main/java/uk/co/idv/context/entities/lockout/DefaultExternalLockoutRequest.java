package uk.co.idv.context.entities.lockout;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;

@Builder
@Data
public class DefaultExternalLockoutRequest implements ExternalLockoutRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

}
