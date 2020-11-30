package uk.co.idv.context.adapter.client;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.idv.context.adapter.client.logger.ClientLogger;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequest;
import uk.co.idv.context.entities.context.Context;
import uk.co.mruoc.json.JsonConverter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static uk.co.idv.context.adapter.client.headers.HeaderConstants.ACCEPT_NAME;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.APPLICATION_JSON;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CONTENT_TYPE_NAME;

@Builder
@Slf4j
public class RestContextClient implements ContextClient {

    private static final String CREATE_CONTEXT_URL = "%s/v1/contexts";
    private static final String GET_CONTEXT_URL = CREATE_CONTEXT_URL + "/%s";
    private static final String RECORD_CONTEXT_RESULT_URL = CREATE_CONTEXT_URL + "/results";

    private final JsonConverter jsonConverter;
    private final String baseUrl;
    private final HttpClient client;
    private final ClientLogger logger;

    @Override
    public Context createContext(ClientCreateContextRequest request) {
        HttpRequest httpRequest = toPostHttpRequest(request);
        HttpResponse<String> httpResponse = send(httpRequest);
        return toContextOrThrowError(httpResponse);
    }

    @Override
    public Context getContext(ClientGetContextRequest request) {
        HttpRequest httpRequest = toGetHttpRequest(request);
        HttpResponse<String> httpResponse = send(httpRequest);
        return toContextOrThrowError(httpResponse);
    }

    @Override
    public Context recordResult(ClientRecordContextResultRequest request) {
        HttpRequest httpRequest = toPatchHttpRequest(request);
        HttpResponse<String> httpResponse = send(httpRequest);
        return toContextOrThrowError(httpResponse);
    }

    private HttpRequest toPostHttpRequest(ClientCreateContextRequest request) {
        return HttpRequest.newBuilder()
                .headers(request.getHeadersArray())
                .header(CONTENT_TYPE_NAME, APPLICATION_JSON)
                .header(ACCEPT_NAME, APPLICATION_JSON)
                .uri(buildCreateUri())
                .POST(HttpRequest.BodyPublishers.ofString(jsonConverter.toJson(request.getBody())))
                .build();
    }

    private HttpRequest toGetHttpRequest(ClientGetContextRequest request) {
        return HttpRequest.newBuilder()
                .headers(request.getHeadersArray())
                .header(ACCEPT_NAME, APPLICATION_JSON)
                .uri(buildGetUrl(request.getId()))
                .GET()
                .build();
    }

    private HttpRequest toPatchHttpRequest(ClientRecordContextResultRequest request) {
        return HttpRequest.newBuilder()
                .headers(request.getHeadersArray())
                .header(CONTENT_TYPE_NAME, APPLICATION_JSON)
                .header(ACCEPT_NAME, APPLICATION_JSON)
                .uri(buildUpdateResultUrl())
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonConverter.toJson(request.getBody())))
                .build();
    }

    private URI buildCreateUri() {
        return toUri(String.format(CREATE_CONTEXT_URL, baseUrl));
    }

    private URI buildGetUrl(UUID id) {
        return toUri(String.format(GET_CONTEXT_URL, baseUrl, id.toString()));
    }

    private URI buildUpdateResultUrl() {
        return toUri(String.format(RECORD_CONTEXT_RESULT_URL, baseUrl));
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

    private Context toContextOrThrowError(HttpResponse<String> response) {
        if (isSuccessful(response)) {
            return jsonConverter.toObject(response.body(), Context.class);
        }
        throw extractException(response);
    }

    private boolean isSuccessful(HttpResponse<String> response) {
        int status = response.statusCode();
        return status >= 200 && status <= 299;
    }

    private RuntimeException extractException(HttpResponse<String> response) {
        ApiError error = jsonConverter.toObject(response.body(), ApiError.class);
        return new ApiErrorClientException(error);
    }

    private static URI toUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new ClientException(e);
        }
    }

}
