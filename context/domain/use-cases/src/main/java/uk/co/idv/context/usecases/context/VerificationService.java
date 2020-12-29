package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;

import java.time.Clock;

@Builder
public class VerificationService {

    private final ContextService contextService;
    private final IdGenerator idGenerator;
    private final Clock clock;

    public Verification create(CreateVerificationRequest request) {
        Context context = contextService.findWithEligibleIncompleteSequences(request.getContextId());
        return Verification.builder()
                .id(idGenerator.generate())
                .contextId(context.getId())
                .created(clock.instant())
                .activity(context.getActivity())
                .protectSensitiveData(context.isProtectSensitiveData())
                .methods(context.getNextMethods(request.getMethodName()))
                .build();
    }

    public Verification complete(boolean successful) {
        return null;
    }

}
