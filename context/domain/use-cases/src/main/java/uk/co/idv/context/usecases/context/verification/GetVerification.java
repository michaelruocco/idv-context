package uk.co.idv.context.usecases.context.verification;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.FindContext;

import java.util.UUID;

@Builder
public class GetVerification {

    private final FindContext findContext;
    private final ContextRepository repository;

    public Verification get(GetVerificationRequest request) {
        UUID contextId = request.getContextId();
        Context context = findContext.find(contextId);
        return context.getVerification(request.getVerificationId());
    }

}
