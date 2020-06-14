package uk.co.idv.context.usecases.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

@Builder
@Data
public class DefaultFindIdentityRequest implements FindIdentityRequest {

    private final Aliases aliases;
    private final Channel channel;

    @Override
    public String getChannelId() {
        return channel.getId();
    }

    @Override
    public CountryCode getCountry() {
        return channel.getCountry();
    }

}
