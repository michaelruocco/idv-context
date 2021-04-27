package uk.co.idv.context.adapter.verification.client.stub;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.VerificationClient;
import uk.co.idv.context.adapter.verification.client.header.DefaultIdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.header.IdvHeaderValidator;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.stub.complete.CompleteVerificationScenario;
import uk.co.idv.context.adapter.verification.client.stub.create.CreateVerificationScenario;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.emptyList;

@Builder
public class StubVerificationClient implements VerificationClient {

    @Builder.Default
    private final IdvHeaderValidator headerValidator = new DefaultIdvHeaderValidator();

    private final CreateVerificationScenario defaultCreateScenario;

    @Builder.Default
    private final Collection<CreateVerificationScenario> createScenarios = emptyList();

    private final CompleteVerificationScenario defaultCompleteScenario;

    @Builder.Default
    private final Collection<CompleteVerificationScenario> completeScenarios = emptyList();

    private final Collection<ClientCreateVerificationRequest> createRequests = new ArrayList<>();
    private final Collection<ClientCompleteVerificationRequest> completeRequests = new ArrayList<>();

    @Override
    public Verification createVerification(ClientCreateVerificationRequest request) {
        createRequests.add(request);
        headerValidator.validate(request.getHeaders());
        return findCreateScenario(request).apply(request);
    }

    @Override
    public CompleteVerificationResult completeVerification(ClientCompleteVerificationRequest request) {
        completeRequests.add(request);
        headerValidator.validate(request.getHeaders());
        return findCompleteScenario(request).apply(request);
    }

    public ClientCreateVerificationRequest getLastCreateRequest() {
        return createRequests.stream()
                .findFirst()
                .orElseThrow(NoRequestsFoundException::new);
    }

    public ClientCompleteVerificationRequest getLastCompleteRequest() {
        return completeRequests.stream()
                .findFirst()
                .orElseThrow(NoRequestsFoundException::new);
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
