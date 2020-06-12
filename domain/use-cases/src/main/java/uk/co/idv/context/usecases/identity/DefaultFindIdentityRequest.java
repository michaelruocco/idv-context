package uk.co.idv.context.usecases.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

import java.time.Duration;

@Builder
@Data
public class DefaultFindIdentityRequest implements FindIdentityRequest {

    @Builder.Default
    private final Aliases aliases = new Aliases();

    private final Alias providedAlias;
    private final Channel channel;
    private final Duration dataLoadTimeout;

    @Override
    public CountryCode getCountry() {
        return channel.getCountry();
    }

    @Override
    public FindIdentityRequest setAliases(Aliases aliases) {
        return DefaultFindIdentityRequest.builder()
                .providedAlias(providedAlias)
                .channel(channel)
                .dataLoadTimeout(dataLoadTimeout)
                .aliases(aliases)
                .build();
    }

}
