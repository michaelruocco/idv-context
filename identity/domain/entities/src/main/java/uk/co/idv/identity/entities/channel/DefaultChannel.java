package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

@Builder
@Data
public class DefaultChannel implements Channel {

    private final String id;
    private final CountryCode country;

    @With
    private final PhoneNumbers phoneNumbers;

    @With
    private final EmailAddresses emailAddresses;

}
