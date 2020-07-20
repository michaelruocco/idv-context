package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.policy.key.CollectionUtils.getFirst;

public class MockPolicyRequestFactory {

    public static PolicyRequest givenPolicyRequestApplyingTo(PolicyKey key) {
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(getFirst(key.getActivityNames()));
        given(request.getAliasType()).willReturn(getFirst(key.getAliasTypes()));
        return request;
    }

}
