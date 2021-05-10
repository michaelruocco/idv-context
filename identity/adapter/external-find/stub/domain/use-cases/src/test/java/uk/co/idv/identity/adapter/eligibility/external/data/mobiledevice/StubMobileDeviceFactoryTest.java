package uk.co.idv.identity.adapter.eligibility.external.data.mobiledevice;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;

import static org.assertj.core.api.Assertions.assertThat;

class StubMobileDeviceFactoryTest {

    private final StubMobileDeviceFactory factory = new StubMobileDeviceFactory();

    @Test
    void shouldReturnName() {
        assertThat(factory.getName()).isEqualTo("MobileDevices");
    }

    @Test
    void shouldReturnEmptyData() {
        assertThat(factory.getEmptyData()).isEmpty();
    }

    @Test
    void shouldReturnPopulatedData() {
        assertThat(factory.getPopulatedData()).isEqualTo(MobileDevicesMother.two());
    }

}
