package uk.co.idv.method.entities.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodsRequestTest {

    @Test
    void shouldReturnMobileDevicesFromIdentity() {
        Collection<String> tokens = Arrays.asList("token-1", "token-2");
        Identity identity = givenIdentityWithMobileDeviceTokens(tokens);

        MethodsRequest request = MethodsRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getMobileDeviceTokens()).isEqualTo(tokens);
    }

    private Identity givenIdentityWithMobileDeviceTokens(Collection<String> tokens) {
        MobileDevices mobileDevices = mock(MobileDevices.class);
        given(mobileDevices.getTokens()).willReturn(tokens);
        Identity identity = mock(Identity.class);
        given(identity.getMobileDevices()).willReturn(mobileDevices);
        return identity;
    }

}
