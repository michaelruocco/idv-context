package uk.co.idv.context.adapter.client;

import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.Verification;

public interface ContextClient {

    Context createContext(ClientCreateContextRequest request);

    Context getContext(ClientGetContextRequest request);

    Verification createVerification(ClientCreateVerificationRequest request);

    //TODO add complete verification

}
