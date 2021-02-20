package uk.co.idv.context.adapter.verification.client.logger;

import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class MdcPopulator {

    private static final String REQUEST_URI = "client-request-uri";
    private static final String REQUEST_URI_TRANSFORMED = "client-request-uri-transformed";
    private static final String REQUEST_METHOD = "client-request-method";
    private static final String REQUEST_STATUS = "client-request-status";
    private static final String REQUEST_DURATION = "client-request-duration";

    private final UnaryOperator<String> uriTransformer;

    public void populate(HttpRequest request) {
        URI uri = request.uri();
        MDC.put(REQUEST_URI, uri.toString());
        MDC.put(REQUEST_URI_TRANSFORMED, uriTransformer.apply(uri.toString()));
        MDC.put(REQUEST_METHOD, request.method());
    }

    public void populate(HttpResponse<String> response) {
        MDC.put(REQUEST_STATUS, Integer.toString(response.statusCode()));
    }

    public void populateDuration(long duration) {
        MDC.put(REQUEST_DURATION, Long.toString(duration));
    }

    public void remove() {
        MDC.remove(REQUEST_URI);
        MDC.remove(REQUEST_URI_TRANSFORMED);
        MDC.remove(REQUEST_METHOD);
        MDC.remove(REQUEST_STATUS);
        MDC.remove(REQUEST_DURATION);
    }

}
