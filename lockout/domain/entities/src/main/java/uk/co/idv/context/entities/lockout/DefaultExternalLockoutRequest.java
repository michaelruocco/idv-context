package uk.co.idv.context.entities.lockout;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;

import java.util.Collection;

@Builder
@Data
public class DefaultExternalLockoutRequest implements ExternalLockoutRequest {

    private final String channelId;
    private final String activityName;
    private final Aliases aliases;

    @Override
    public Collection<String> getAliasTypes() {
        return aliases.getTypes();
    }

}
