package uk.co.idv.context.usecases.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

@Builder
public class DefaultLoadIdentityRequest implements LoadIdentityRequest {

    @Builder.Default private final Aliases aliases = new Aliases();

    private final Alias providedAlias;
    private final Channel channel;

    @Override
    public Alias getProvidedAlias() {
        return providedAlias;
    }

    @Override
    public Aliases getAliases() {
        return aliases;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public CountryCode getCountry() {
        return channel.getCountry();
    }

    @Override
    public LoadIdentityRequest setAliases(Aliases aliases) {
        return DefaultLoadIdentityRequest.builder()
                .providedAlias(providedAlias)
                .aliases(aliases)
                .channel(channel)
                .build();
    }

}
