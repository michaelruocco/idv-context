package uk.co.idv.context.adapter.verification.client.stub;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.header.IdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.stub.complete.CompleteVerificationScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;

import java.util.Collection;

@Builder
public class StubVerificationClient implements VerificationClient {

    @Builder.Default
    private final IdvHeaderValidator headerValidator = new IdvHeaderValidator();

    private final CreateVerificationScenario defaultCreateScenario;
    private final Collection<CreateVerificationScenario> createScenarios;

    private final CompleteVerificationScenario defaultCompleteScenario;
    private final Collection<CompleteVerificationScenario> completeScenarios;

    @Override
    public Verification createVerification(ClientCreateVerificationRequest request) {
        headerValidator.validate(request.getHeaders());
        return findCreateScenario(request).apply(request);
    }

    @Override
    public CompleteVerificationResult completeVerification(ClientCompleteVerificationRequest request) {
        headerValidator.validate(request.getHeaders());
        return findCompleteScenario(request).apply(request);
    }

    private CreateVerificationScenario findCreateScenario(ClientCreateVerificationRequest request) {
        return createScenarios.stream()
                .filter(scenario -> scenario.shouldExecute(request))
                .findFirst()
                .orElse(defaultCreateScenario);
    }

    private CompleteVerificationScenario findCompleteScenario(ClientCompleteVerificationRequest request) {
        return completeScenarios.stream()
                .filter(scenario -> scenario.shouldExecute(request))
                .findFirst()
                .orElse(defaultCompleteScenario);
    }

}
