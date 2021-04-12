package uk.co.idv.context.adapter.verification.client.stub.create;

import lombok.Builder;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.awaitility.Awaitility.await;

@Builder
public class CreateVerificationSuccessScenario implements CreateVerificationScenario {

    @Builder.Default
    private final Duration delay = Duration.ZERO;
    private final Clock clock;
    private final Methods methods;
    private final Activity activity;

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return true;
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        await().pollDelay(delay).until(() -> true);
        Instant created = clock.instant();
        return VerificationMother.builder()
                .contextId(request.getContextId())
                .created(created)
                .expiry(created.plus(methods.getLongestDuration()))
                .activity(activity)
                .methods(methods)
                .build();
    }

}
