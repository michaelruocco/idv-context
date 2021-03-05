package uk.co.idv.policy.entities.policy.key.validcookie;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ValidCookiePolicyKeyTest {

    private final PolicyKey baseKey = mock(PolicyKey.class);

    private final ValidCookiePolicyKey key = ValidCookiePolicyKey.builder()
            .baseKey(baseKey)
            .validCookie(true)
            .build();

    @Test
    void shouldReturnType() {
        assertThat(key.getType()).isEqualTo("valid-cookie");
    }

    @Test
    void shouldReturnValidCookie() {
        assertThat(key.isValidCookie()).isTrue();
    }

    @Test
    void shouldReturnIdFromBaseKey() {
        UUID expectedId = UUID.randomUUID();
        given(baseKey.getId()).willReturn(expectedId);

        UUID id = key.getId();

        assertThat(id).isEqualTo(expectedId);
    }

    @Test
    void shouldReturnChannelIdFromBaseKey() {
        String expectedChannelId = "channel-id";
        given(baseKey.getChannelId()).willReturn(expectedChannelId);

        String channelId = key.getChannelId();

        assertThat(channelId).isEqualTo(expectedChannelId);
    }

    @Test
    void shouldReturnPriorityFromBaseKey() {
        int expectedPriority = 3;
        given(baseKey.getPriority()).willReturn(expectedPriority);

        int priority = key.getPriority();

        assertThat(priority).isEqualTo(expectedPriority);
    }

    @Test
    void shouldReturnActivityNamesFromBaseKey() {
        Collection<String> expectedActivityNames = Collections.singleton("activity-name");
        given(baseKey.getActivityNames()).willReturn(expectedActivityNames);

        Collection<String> activityNames = key.getActivityNames();

        assertThat(activityNames).isEqualTo(expectedActivityNames);
    }

    @Test
    void shouldNotApplyIfRequestDoesNotApplyToBaseKey() {
        PolicyRequest request = mock(PolicyRequest.class);
        given(baseKey.appliesTo(request)).willReturn(false);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyIfRequestAppliesToBaseKeyButIsNotValidCookieRequest() {
        PolicyRequest request = mock(PolicyRequest.class);
        given(baseKey.appliesTo(request)).willReturn(true);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyIfRequestAppliesToBaseKeyButValidCookieFlagDoesNotMatch() {
        ValidCookiePolicyRequest request = mock(ValidCookiePolicyRequest.class);
        given(baseKey.appliesTo(request)).willReturn(true);
        given(request.hasValidCookie()).willReturn(false);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyIfRequestAppliesToBaseKeyAndValidCookieFlagMatches() {
        ValidCookiePolicyRequest request = mock(ValidCookiePolicyRequest.class);
        given(baseKey.appliesTo(request)).willReturn(true);
        given(request.hasValidCookie()).willReturn(true);

        boolean applies = key.appliesTo(request);

        assertThat(applies).isTrue();
    }
}
