package uk.co.idv.context.adapter.verification.client.stub.create;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.adapter.json.error.contextnotfound.ContextNotFoundError;
import uk.co.idv.method.entities.verification.Verification;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateVerificationNotFoundScenario implements CreateVerificationScenario {

    public static final UUID DEFAULT_ID = UUID.fromString("9ed739ec-a252-4a3f-840c-4e2bdccf56e6");

    private final UUID id;

    public CreateVerificationNotFoundScenario() {
        this(DEFAULT_ID);
    }

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return id.equals(request.getContextId());
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        throw new ApiErrorClientException(new ContextNotFoundError(request.getContextId().toString()));
    }

}
