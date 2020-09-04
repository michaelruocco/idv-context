package uk.co.idv.context.usecases.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;

@Slf4j
public class ContextPolicyService {

    public ContextPolicy load(CreateContextRequest request) {
        log.info("loading context with request {}", request);
        return new ContextPolicy(new SequencePolicies());
    }

}
