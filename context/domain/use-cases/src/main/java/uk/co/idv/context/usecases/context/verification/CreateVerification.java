package uk.co.idv.context.usecases.context.verification;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.FindContext;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Builder
public class CreateVerification {

    private final FindContext findContext;
    private final UuidGenerator uuidGenerator;
    private final Clock clock;
    private final ContextRepository repository;

    public Verification create(CreateVerificationRequest request) {
        UUID contextId = request.getContextId();
        Context context = findContext.find(contextId);
        Verification verification = build(request.getMethodName(), context);
        Context updated = context.add(verification);
        repository.save(updated);
        return verification;
    }

    private Verification build(String methodName, Context context) {
        Instant created = clock.instant();
        Methods methods = context.getNextMethods(methodName);
        return Verification.builder()
                .id(uuidGenerator.generate())
                .contextId(context.getId())
                .activity(context.getActivity())
                .methodName(methodName)
                .methods(methods)
                .protectSensitiveData(context.isProtectSensitiveData())
                .created(created)
                .expiry(created.plus(methods.getShortestDuration()))
                .build();
    }

}
