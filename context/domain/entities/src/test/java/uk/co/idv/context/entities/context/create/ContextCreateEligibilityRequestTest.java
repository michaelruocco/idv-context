package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;

import static org.assertj.core.api.Assertions.assertThat;

class ContextCreateEligibilityRequestTest {

    @Test
    void shouldReturnInitialRequest() {
        CreateContextRequest initialRequest = FacadeCreateContextRequestMother.build();

        ContextCreateEligibilityRequest request = ContextCreateEligibilityRequest.builder()
                .request(initialRequest)
                .build();

        assertThat(request.getRequest()).isEqualTo(initialRequest);
    }

    @Test
    void shouldReturnAliasesFromInitialRequest() {
        CreateContextRequest initialRequest = FacadeCreateContextRequestMother.build();

        ContextCreateEligibilityRequest request = ContextCreateEligibilityRequest.builder()
                .request(initialRequest)
                .build();

        assertThat(request.getAliases()).isEqualTo(initialRequest.getAliases());
    }

    @Test
    void shouldReturnChannelFromIdentityRequest() {
        CreateContextRequest initialRequest = FacadeCreateContextRequestMother.build();

        ContextCreateEligibilityRequest request = ContextCreateEligibilityRequest.builder()
                .request(initialRequest)
                .build();

        assertThat(request.getChannel()).isEqualTo(initialRequest.getChannel());
    }

    @Test
    void shouldReturnPolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        ContextCreateEligibilityRequest request = ContextCreateEligibilityRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnRequestedDataFromPolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        ContextCreateEligibilityRequest request = ContextCreateEligibilityRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getRequestedData()).isEqualTo(policy.getRequestedData());
    }

}
