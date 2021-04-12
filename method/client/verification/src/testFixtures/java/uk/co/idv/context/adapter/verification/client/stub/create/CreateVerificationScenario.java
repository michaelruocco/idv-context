package uk.co.idv.context.adapter.verification.client.stub.create;

import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;

import java.util.function.Function;

public interface CreateVerificationScenario extends Function<ClientCreateVerificationRequest, Verification> {

    boolean shouldExecute(ClientCreateVerificationRequest request);

}
