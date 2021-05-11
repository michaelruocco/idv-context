package uk.co.idv.method.entities.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodsRequestTest {

    @Test
    void shouldReturnMobileDevicesFromIdentity() {
        MobileDevices mobileDevices = mock(MobileDevices.class);
        Identity identity = givenIdentityWith(mobileDevices);

        MethodsRequest request = MethodsRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getMobileDevices()).isEqualTo(mobileDevices);
    }

    private Identity givenIdentityWith(MobileDevices mobileDevices) {
        Identity identity = mock(Identity.class);
        given(identity.getMobileDevices()).willReturn(mobileDevices);
        return identity;
    }

}
