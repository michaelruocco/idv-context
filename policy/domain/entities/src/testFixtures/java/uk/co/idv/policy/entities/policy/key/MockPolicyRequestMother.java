package uk.co.idv.policy.entities.policy.key;

import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.policy.entities.policy.key.CollectionUtils.getFirst;

public interface MockPolicyRequestMother {

    static PolicyRequest applyingTo(PolicyKey key) {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(getFirst(key.getActivityNames()));
        given(request.getAliasTypes()).willReturn(key.getAliasTypes());
        return request;
    }

}
