package uk.co.idv.identity.entities.mobiledevice;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class MobileDevicesTest {

    private final MobileDevice device1 = MobileDeviceMother.device1();
    private final MobileDevice device2 = MobileDeviceMother.device2();

    private final MobileDevices devices = MobileDevicesMother.with(device1, device2);

    @Test
    void shouldBeIterable() {
        assertThat(devices).containsExactly(device1, device2);
    }

    @Test
    void shouldReturnStream() {
        assertThat(devices.stream()).containsExactly(device1, device2);
    }

    @Test
    void shouldReturnIsEmptyFalseIfNotEmpty() {
        assertThat(devices.isEmpty()).isFalse();
    }

    @Test
    void shouldReturnIsEmptyTrueIfEmpty() {
        MobileDevices emptyDevices = MobileDevicesMother.empty();

        assertThat(emptyDevices.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnDeviceTokens() {
        Collection<String> tokens = devices.getTokens();

        assertThat(tokens).containsExactly(
                "8cc1121057f63af3c57bbe",
                "2dd1121057f63af3c57ccf"
        );
    }

    @Test
    void shouldAddMobileDevicesWithoutDuplicates() {
        MobileDevice device3 = MobileDeviceMother.of("3ee1121057f63af3c57ddg");
        MobileDevices otherDevices = new MobileDevices(device2, device3);

        MobileDevices mergedDevices = devices.add(otherDevices);

        assertThat(mergedDevices.stream()).containsExactly(device1, device2, device3);
    }

}
