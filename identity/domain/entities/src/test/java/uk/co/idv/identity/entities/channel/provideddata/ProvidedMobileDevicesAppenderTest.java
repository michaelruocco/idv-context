package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.channel.fake.FakeChannelMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class ProvidedMobileDevicesAppenderTest {

    private final Identity identity = IdentityMother.withMobileDevices(MobileDevicesMother.with("existing-device"));

    @Test
    void shouldAppendMobileDevicesToIdentityIfChannelProvidesMobileDevices() {
        MobileDevices providedDevices = MobileDevicesMother.with("provided-device");
        Channel channel = DefaultChannelMother.withMobileDevices(providedDevices);
        UnaryOperator<Identity> appender = new ProvidedMobileDevicesAppender(channel);

        Identity updated = appender.apply(identity);

        MobileDevices expectedDevices = identity.getMobileDevices().add(providedDevices);
        assertThat(updated.getMobileDevices()).containsExactlyElementsOf(expectedDevices);
    }

    @Test
    void shouldReturnInputIdentityUnchangedIfChannelDoesNotProvidedEmailAddresses() {
        Channel channel = FakeChannelMother.build();
        UnaryOperator<Identity> appender = new ProvidedMobileDevicesAppender(channel);

        Identity updated = appender.apply(identity);

        assertThat(updated).isEqualTo(identity);
    }

}
