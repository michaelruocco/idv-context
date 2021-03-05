package uk.co.idv.context.usecases.context.identity;

import lombok.Builder;
import uk.co.idv.context.entities.context.create.ContextCreateEligibilityRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.policy.entities.policy.PolicyRequest;

@Builder
public class IdentityLoader {

    private final ContextPolicyService policyService;
    private final CreateEligibility createEligibility;

    @Builder.Default
    private final PolicyRequestFactory policyRequestFactory = new PolicyRequestFactory();

    public ServiceCreateContextRequest addIdentity(CreateContextRequest request) {
        ContextCreateEligibilityRequest eligibilityRequest = loadAndAddPolicy(request);
        IdentityEligibility eligibility = createEligibility.create(eligibilityRequest);
        return ServiceCreateContextRequest.builder()
                .initial(request)
                .policy(eligibilityRequest.getPolicy())
                .identity(eligibility.getIdentity())
                .build();
    }

    private ContextCreateEligibilityRequest loadAndAddPolicy(CreateContextRequest request) {
        PolicyRequest policyRequest = policyRequestFactory.toPolicyRequest(request);
        return ContextCreateEligibilityRequest.builder()
                .request(request)
                .policy(policyService.loadHighestPriority(policyRequest))
                .build();
    }

}
