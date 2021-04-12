package uk.co.idv.context.adapter.verification.client.stub.create;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.adapter.json.error.notnextmethod.NotNextMethodError;
import uk.co.idv.method.entities.verification.Verification;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateVerificationNotNextMethodScenario implements CreateVerificationScenario {

    public static final UUID DEFAULT_ID = UUID.fromString("e4505f71-5c2b-406f-a640-49c9c940aa5b");

    private final UUID id;

    public CreateVerificationNotNextMethodScenario() {
        this(DEFAULT_ID);
    }

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return id.equals(request.getContextId());
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        throw new ApiErrorClientException(new NotNextMethodError(request.getMethodName()));
    }

}
