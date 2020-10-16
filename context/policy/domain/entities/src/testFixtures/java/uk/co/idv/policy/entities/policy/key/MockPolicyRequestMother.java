package uk.co.idv.policy.entities.policy.key;

import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockPolicyRequestMother {

    static PolicyRequest applyingTo(PolicyKey key) {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        given(request.getChannelId()).willReturn(key.getChannelId());
        given(request.getActivityName()).willReturn(IterableUtils.first(key.getActivityNames()));
        given(request.getAliasTypes()).willReturn(key.getAliasTypes());
        return request;
    }

}
