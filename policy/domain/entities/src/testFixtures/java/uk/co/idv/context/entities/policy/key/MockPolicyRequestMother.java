package uk.co.idv.context.entities.policy.key;

import uk.co.idv.context.entities.policy.DefaultPolicyRequest;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.entities.policy.key.CollectionUtils.getFirst;

public interface MockPolicyRequestMother {

    static PolicyRequest applyingTo(PolicyKey key) {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(getFirst(key.getActivityNames()));
        given(request.getAliasTypes()).willReturn(key.getAliasTypes());
        return request;
    }

}
