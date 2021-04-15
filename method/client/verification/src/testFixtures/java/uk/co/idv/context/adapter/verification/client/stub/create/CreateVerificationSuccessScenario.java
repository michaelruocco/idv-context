package uk.co.idv.context.adapter.verification.client.stub.create;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;

import java.time.Duration;
import java.util.function.Function;

import static org.awaitility.Awaitility.await;

@Builder
public class CreateVerificationSuccessScenario implements CreateVerificationScenario {

    @Builder.Default
    private final Duration delay = Duration.ZERO;
    private final Function<ClientCreateVerificationRequest, Verification> verificationFactory;

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return true;
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        await().pollDelay(delay).until(() -> true);
        return verificationFactory.apply(request);
    }

}
