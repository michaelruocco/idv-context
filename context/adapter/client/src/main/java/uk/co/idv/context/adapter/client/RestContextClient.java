package uk.co.idv.context.adapter.client;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.idv.context.adapter.client.logger.ClientLogger;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequest;
import uk.co.idv.context.entities.context.Context;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Builder
@Slf4j
public class RestContextClient implements ContextClient {

    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;
    private final HttpClient client;
    private final ClientLogger logger;

    @Override
    public Context createContext(ClientCreateContextRequest request) {
        HttpRequest httpRequest = requestConverter.toPostHttpRequest(request);
        HttpResponse<String> httpResponse = send(httpRequest);
        return responseConverter.toContextOrThrowError(httpResponse);
    }

    @Override
    public Context getContext(ClientGetContextRequest request) {
        HttpRequest httpRequest = requestConverter.toGetHttpRequest(request);
        HttpResponse<String> httpResponse = send(httpRequest);
        return responseConverter.toContextOrThrowError(httpResponse);
    }

    @Override
    public Context recordResult(ClientRecordContextResultRequest request) {
        HttpRequest httpRequest = requestConverter.toPatchHttpRequest(request);
        HttpResponse<String> httpResponse = send(httpRequest);
        return responseConverter.toContextOrThrowError(httpResponse);
    }

    private HttpResponse<String> send(HttpRequest request) {
        try {
            logger.log(request);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.log(response);
            return response;
        } catch (IOException e) {
            throw new ClientException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException(e);
        }
    }

}
