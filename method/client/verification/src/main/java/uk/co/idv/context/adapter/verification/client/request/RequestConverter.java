package uk.co.idv.context.adapter.verification.client.request;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.verification.client.exception.ClientException;
import uk.co.idv.context.adapter.verification.client.header.IdvHeaderConstants;
import uk.co.mruoc.json.JsonConverter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

@Builder
@Slf4j
public class RequestConverter {

    private static final String VERIFICATION_URL = "%s/v1/contexts/verifications";

    private final JsonConverter jsonConverter;
    private final String baseUri;

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
                .header(IdvHeaderConstants.CONTENT_TYPE_NAME, IdvHeaderConstants.APPLICATION_JSON)
                .header(IdvHeaderConstants.ACCEPT_NAME, IdvHeaderConstants.APPLICATION_JSON);
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
