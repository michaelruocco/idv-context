package uk.co.idv.context.usecases.identity.request;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.usecases.identity.service.find.FindIdentityRequest;

@Builder
@Data
public class ExternalFindIdentityRequest implements FindIdentityRequest {

    private final Aliases aliases;
    private final Channel channel;

    public String getChannelId() {
        return channel.getId();
    }

    public CountryCode getCountry() {
        return channel.getCountry();
    }

}
