package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.mruoc.json.JsonConverter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.UUID;

import static uk.co.idv.context.adapter.client.header.HeaderConstants.ACCEPT_NAME;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.APPLICATION_JSON;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.CONTENT_TYPE_NAME;

@Builder
@Slf4j
public class RequestConverter {

    private static final String POST_CONTEXT_URL = "%s/v1/contexts";
    private static final String GET_CONTEXT_URL = POST_CONTEXT_URL + "/%s";
    private static final String VERIFICATION_URL = POST_CONTEXT_URL + "/verifications";

    private final JsonConverter jsonConverter;
    private final String baseUri;

    public HttpRequest toPostContextHttpRequest(ClientCreateContextRequest request) {
        return httpRequestWithBodyBuilder()
                .headers(request.getHeadersArray())
                .uri(buildCreateUri())
                .POST(HttpRequest.BodyPublishers.ofString(jsonConverter.toJson(request.getBody())))
                .build();
    }

    public HttpRequest toGetContextHttpRequest(ClientGetContextRequest request) {
        return HttpRequest.newBuilder()
                .headers(request.getHeadersArray())
                .header(ACCEPT_NAME, APPLICATION_JSON)
                .uri(buildGetUrl(request.getId()))
                .GET()
                .build();
    }

    public HttpRequest toPostVerificationHttpRequest(ClientCreateVerificationRequest request) {
        return httpRequestWithBodyBuilder()
                .headers(request.getHeadersArray())
                .uri(buildVerificationUrl())
                .POST(HttpRequest.BodyPublishers.ofString(jsonConverter.toJson(request.getBody())))
                .build();
    }

    public HttpRequest toPatchVerificationHttpRequest(ClientCompleteVerificationRequest request) {
        return httpRequestWithBodyBuilder()
                .headers(request.getHeadersArray())
                .uri(buildVerificationUrl())
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonConverter.toJson(request.getBody())))
                .build();
    }

    private HttpRequest.Builder httpRequestWithBodyBuilder() {
        return HttpRequest.newBuilder()
                .header(CONTENT_TYPE_NAME, APPLICATION_JSON)
                .header(ACCEPT_NAME, APPLICATION_JSON);
    }

    private URI buildCreateUri() {
        return toUri(String.format(POST_CONTEXT_URL, baseUri));
    }

    private URI buildGetUrl(UUID contextId) {
        return toUri(String.format(GET_CONTEXT_URL, baseUri, contextId.toString()));
    }

    private URI buildVerificationUrl() {
        return toUri(String.format(VERIFICATION_URL, baseUri));
    }

    private static URI toUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new ClientException(e);
        }
    }

}
