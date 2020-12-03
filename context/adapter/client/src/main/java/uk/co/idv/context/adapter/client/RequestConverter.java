package uk.co.idv.context.adapter.client;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequest;
import uk.co.mruoc.json.JsonConverter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.UUID;

import static uk.co.idv.context.adapter.client.headers.HeaderConstants.ACCEPT_NAME;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.APPLICATION_JSON;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CONTENT_TYPE_NAME;

@Builder
@Slf4j
public class RequestConverter {

    private static final String CREATE_CONTEXT_URL = "%s/v1/contexts";
    private static final String GET_CONTEXT_URL = CREATE_CONTEXT_URL + "/%s";
    private static final String RECORD_CONTEXT_RESULT_URL = CREATE_CONTEXT_URL + "/results";

    private final JsonConverter jsonConverter;
    private final String baseUrl;

    public HttpRequest toPostHttpRequest(ClientCreateContextRequest request) {
        return HttpRequest.newBuilder()
                .headers(request.getHeadersArray())
                .header(CONTENT_TYPE_NAME, APPLICATION_JSON)
                .header(ACCEPT_NAME, APPLICATION_JSON)
                .uri(buildCreateUri())
                .POST(HttpRequest.BodyPublishers.ofString(jsonConverter.toJson(request.getBody())))
                .build();
    }

    public HttpRequest toGetHttpRequest(ClientGetContextRequest request) {
        return HttpRequest.newBuilder()
                .headers(request.getHeadersArray())
                .header(ACCEPT_NAME, APPLICATION_JSON)
                .uri(buildGetUrl(request.getId()))
                .GET()
                .build();
    }

    public HttpRequest toPatchHttpRequest(ClientRecordContextResultRequest request) {
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

    private static URI toUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new ClientException(e);
        }
    }

}