package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;

class DataAppenderFactoryTest {

    private final DataAppenderFactory factory = new DataAppenderFactory();

    @Test
    void shouldBuildAppenderThatWillAppendPhoneNumbersToIdentityIfProvided() {
        PhoneNumbers phoneNumbers = PhoneNumbersMother.two();
        Channel channel = DefaultChannelMother.withPhoneNumbers(phoneNumbers);
        DataAppender appender = factory.build(channel);
        Identity identity = IdentityMother.withPhoneNumbers(PhoneNumbersMother.empty());

        Identity updated = appender.apply(identity);

        assertThat(updated).usingRecursiveComparison().ignoringFields("phoneNumbers").isEqualTo(identity);
        assertThat(updated.getPhoneNumbers()).containsExactlyElementsOf(phoneNumbers);
    }

    @Test
    void shouldBuildAppenderThatWillAppendEmailAddressesToIdentityIfProvided() {
        EmailAddresses emailAddresses = EmailAddressesMother.two();
        Channel channel = DefaultChannelMother.withEmailAddresses(emailAddresses);
        DataAppender appender = factory.build(channel);
        Identity identity = IdentityMother.withEmailAddresses(EmailAddressesMother.empty());

        Identity updated = appender.apply(identity);

        assertThat(updated).usingRecursiveComparison().ignoringFields("emailAddresses").isEqualTo(identity);
        assertThat(updated.getEmailAddresses()).containsExactlyElementsOf(emailAddresses);
    }

    @Test
    void shouldBuildAppenderThatWillAppendMobileDevicesToIdentityIfProvided() {
        MobileDevices devices = MobileDevicesMother.two();
        Channel channel = DefaultChannelMother.withMobileDevices(devices);
        DataAppender appender = factory.build(channel);
        Identity identity = IdentityMother.withMobileDevices(MobileDevicesMother.empty());

        Identity updated = appender.apply(identity);

        assertThat(updated).usingRecursiveComparison().ignoringFields("mobileDevices").isEqualTo(identity);
        assertThat(updated.getMobileDevices()).containsExactlyElementsOf(devices);
    }

}
