package uk.co.idv.context.adapter.verification.client.stub.complete;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.CompleteVerificationResultMother;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@Builder
public class CompleteVerificationSuccessScenario implements CompleteVerificationScenario {

    @Builder.Default
    private final Duration delay = Duration.ZERO;

    @Override
    public boolean shouldExecute(ClientCompleteVerificationRequest request) {
        return true;
    }

    @Override
    public CompleteVerificationResult apply(ClientCompleteVerificationRequest clientCompleteVerificationRequest) {
        await().pollDelay(delay).until(() -> true);
        return CompleteVerificationResultMother.successful();
    }

}
