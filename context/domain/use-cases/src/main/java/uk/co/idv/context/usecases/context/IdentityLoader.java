package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.context.entities.context.create.ContextCreateEligibilityRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.policy.ContextPolicyService;

@Builder
public class IdentityLoader {

    private final ContextPolicyService policyService;
    private final CreateEligibility createEligibility;

    public IdentityCreateContextRequest addIdentity(CreateContextRequest request) {
        ContextCreateEligibilityRequest eligibilityRequest = loadAndAddPolicy(request);
        Eligibility eligibility = createEligibility.create(eligibilityRequest);
        return IdentityCreateContextRequest.builder()
                .initial(request)
                .policy(eligibilityRequest.getPolicy())
                .identity(eligibility.getIdentity())
                .build();
    }

    private ContextCreateEligibilityRequest loadAndAddPolicy(CreateContextRequest request) {
        return ContextCreateEligibilityRequest.builder()
                .request(request)
                .policy(policyService.load(request))
                .build();
    }

}
