package uk.co.idv.identity.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.ValidCookieChannel;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

@Data
@Builder
public class Abc implements Channel, ValidCookieChannel {

    public static final String ID = "abc";

    @Builder.Default
    private final PhoneNumbers phoneNumbers = new PhoneNumbers();

    @Builder.Default
    private final EmailAddresses emailAddresses = new EmailAddresses();

    private final boolean validCookie;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

    @Override
    public boolean hasValidCookie() {
        return validCookie;
    }

}
