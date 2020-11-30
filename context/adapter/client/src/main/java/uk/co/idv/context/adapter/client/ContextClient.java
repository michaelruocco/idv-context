package uk.co.idv.context.adapter.client;

import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequest;
import uk.co.idv.context.entities.context.Context;

public interface ContextClient {

    Context createContext(ClientCreateContextRequest request);

    Context getContext(ClientGetContextRequest request);

    Context recordResult(ClientRecordContextResultRequest request);

}
