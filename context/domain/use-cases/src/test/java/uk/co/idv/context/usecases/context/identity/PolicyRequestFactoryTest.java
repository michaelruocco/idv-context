package uk.co.idv.context.usecases.context.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.validcookie.ValidCookiePolicyRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PolicyRequestFactoryTest {

    private final CreateContextRequest createContextRequest = mock(CreateContextRequest.class);

    private final PolicyRequestFactory factory = new PolicyRequestFactory();

    @Test
    void shouldPopulateChannelIdOnPolicyRequest() {
        givenChannelDoesNotSupportValidCookie();
        String channelId = givenReturnsChannelId();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPopulateActivityNameOnPolicyRequest() {
        givenChannelDoesNotSupportValidCookie();
        String activityName = givenReturnsActivityName();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPopulateAliasTypesOnPolicyRequest() {
        givenChannelDoesNotSupportValidCookie();
        Collection<String> aliasTypes = givenReturnsAliasTypes();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest.getAliasTypes()).isEqualTo(aliasTypes);
    }

    @Test
    void shouldReturnValidCookiePolicyRequestIfChannelSupportsValidCookie() {
        givenChannelSupportsValidCookie();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest).isInstanceOf(ValidCookiePolicyRequest.class);
        ValidCookiePolicyRequest validCookiePolicyRequest = (ValidCookiePolicyRequest) policyRequest;
        assertThat(validCookiePolicyRequest.hasValidCookie()).isTrue();
    }

    @Test
    void shouldPopulateChannelIdOnValidCookiePolicyRequest() {
        givenChannelSupportsValidCookie();
        String channelId = givenReturnsChannelId();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPopulateActivityNameOnValidCookiePolicyRequest() {
        givenChannelSupportsValidCookie();
        String activityName = givenReturnsActivityName();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPopulateAliasTypesOnValidCookiePolicyRequest() {
        givenChannelSupportsValidCookie();
        Collection<String> aliasTypes = givenReturnsAliasTypes();

        PolicyRequest policyRequest = factory.toPolicyRequest(createContextRequest);

        assertThat(policyRequest.getAliasTypes()).isEqualTo(aliasTypes);
    }

    private void givenChannelDoesNotSupportValidCookie() {
        Channel channel = mock(Channel.class);
        given(channel.hasValidCookie()).willReturn(Optional.empty());
        given(createContextRequest.getChannel()).willReturn(channel);
    }

    private String givenReturnsChannelId() {
        String channelId = "channel-id";
        given(createContextRequest.getChannelId()).willReturn(channelId);
        return channelId;
    }

    private String givenReturnsActivityName() {
        String activityName = "activity-name";
        given(createContextRequest.getActivityName()).willReturn(activityName);
        return activityName;
    }

    private Collection<String> givenReturnsAliasTypes() {
        Collection<String> types = Arrays.asList("alias-type-1", "alias-type-2");
        given(createContextRequest.getAliasTypes()).willReturn(types);
        return types;
    }

    private void givenChannelSupportsValidCookie() {
        Channel channel = mock(Channel.class);
        given(channel.hasValidCookie()).willReturn(Optional.of(true));
        given(createContextRequest.getChannel()).willReturn(channel);
    }

}
