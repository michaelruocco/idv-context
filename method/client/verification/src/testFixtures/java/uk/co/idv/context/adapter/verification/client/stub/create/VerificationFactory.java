package uk.co.idv.context.adapter.verification.client.stub.create;

import lombok.Builder;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.time.Clock;
import java.time.Instant;
import java.util.function.Function;

@Builder
public class VerificationFactory implements Function<ClientCreateVerificationRequest, Verification> {

    private final Clock clock;
    private final Methods methods;
    private final Activity activity;
    private final boolean protectSensitiveData;

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        Instant created = clock.instant();
        return VerificationMother.builder()
                .contextId(request.getContextId())
                .created(created)
                .expiry(created.plus(methods.getLongestDuration()))
                .activity(activity)
                .methods(methods)
                .protectSensitiveData(protectSensitiveData)
                .build();
    }

}
