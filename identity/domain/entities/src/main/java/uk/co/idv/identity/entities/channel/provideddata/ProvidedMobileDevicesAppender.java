package uk.co.idv.identity.entities.channel.provideddata;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;

import java.util.Optional;

@RequiredArgsConstructor
public class ProvidedMobileDevicesAppender implements DataAppender {

    private final Channel channel;

    @Override
    public Identity apply(Identity identity) {
        return addProvidedMobileDevicesIfPresent(identity.getMobileDevices())
                .map(identity::withMobileDevices)
                .orElse(identity);
    }

    private Optional<MobileDevices> addProvidedMobileDevicesIfPresent(MobileDevices mobileDevices) {
        return Optional.of(mobileDevices.add(channel.getMobileDevices()));
    }

}
