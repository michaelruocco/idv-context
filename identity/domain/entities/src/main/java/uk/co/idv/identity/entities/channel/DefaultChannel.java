package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.channel.provideddata.EmailAddressesProvider;
import uk.co.idv.identity.entities.channel.provideddata.PhoneNumbersProvider;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

@Builder
@Data
public class DefaultChannel implements Channel, PhoneNumbersProvider, EmailAddressesProvider {

    private final String id;
    private final CountryCode country;
    private final PhoneNumbers phoneNumbers;
    private final EmailAddresses emailAddresses;

}
