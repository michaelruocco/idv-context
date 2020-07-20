package uk.co.idv.context.policy.key;

import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockPolicyRequestFactory {

    public static PolicyRequest givenPolicyRequestApplyingTo(PolicyKey key) {
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        if (key.hasActivityNames()) {
            given(request.getActivityName()).willReturn(getFirst(key.getActivityNames()));
        }
        if (key.hasAliasTypes()) {
            given(request.getAliasType()).willReturn(getFirst(key.getAliasTypes()));
        }
        return request;
    }

    private static String getFirst(Collection<String> values) {
        return IterableUtils.get(values, 0);
    }

}
