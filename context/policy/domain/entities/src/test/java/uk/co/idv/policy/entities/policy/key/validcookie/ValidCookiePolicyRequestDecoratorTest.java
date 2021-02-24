package uk.co.idv.policy.entities.policy.key.validcookie;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;
import uk.co.idv.policy.entities.policy.key.validcookie.ValidCookiePolicyRequestDecorator.ValidCookiePolicyRequestDecoratorBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class ValidCookiePolicyRequestDecoratorTest {

    private final ValidCookiePolicyRequestDecoratorBuilder builder = ValidCookiePolicyRequestDecorator.builder();

    @Test
    void shouldReturnChannelIdFromBaseRequest() {
        PolicyRequest baseRequest = PolicyRequestMother.build();

        PolicyRequest request = builder.baseRequest(baseRequest).build();

        assertThat(request.getChannelId()).isEqualTo(baseRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromBaseRequest() {
        PolicyRequest baseRequest = PolicyRequestMother.build();

        PolicyRequest request = builder.baseRequest(baseRequest).build();

        assertThat(request.getActivityName()).isEqualTo(baseRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasTypesFromBaseRequest() {
        PolicyRequest baseRequest = PolicyRequestMother.build();

        PolicyRequest request = builder.baseRequest(baseRequest).build();

        assertThat(request.getAliasTypes()).isEqualTo(baseRequest.getAliasTypes());
    }

    @Test
    void shouldReturnValidCookie() {
        builder.validCookie(true);

        ValidCookiePolicyRequest request = builder.build();

        assertThat(request.hasValidCookie()).isTrue();
    }

}
