package uk.co.idv.context.usecases.eligibility;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentityRequest;

import java.util.Collection;

@Builder
@Data
public class CreateEligibilityRequest implements ExternalFindIdentityRequest {

    private final Aliases aliases;
    private final Channel channel;
    private final Collection<String> requested;

    @Override
    public String getChannelId() {
        return channel.getId();
    }

    @Override
    public CountryCode getCountry() {
        return channel.getCountry();
    }

    @Override
    public boolean emailAddressesRequested() {
        return requested.contains("email-addresses");
    }

    @Override
    public boolean phoneNumbersRequested() {
        return requested.contains("phone-numbers");
    }

}
