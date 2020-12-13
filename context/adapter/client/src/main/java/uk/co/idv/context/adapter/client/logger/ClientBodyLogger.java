package uk.co.idv.context.adapter.client.logger;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.UnaryOperator;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Builder
public class ClientBodyLogger implements ClientLogger {

    private static final String REQUEST_URI = "client-request-uri";
    private static final String REQUEST_URI_TRANSFORMED = "client-request-uri-transformed";
    private static final String REQUEST_METHOD = "client-request-method";
    private static final String REQUEST_STATUS = "client-request-status";
    private static final String REQUEST_DURATION = "client-request-duration";

    private final UnaryOperator<String> uriTransformer;
    private final UnaryOperator<String> requestMasker;
    private final UnaryOperator<String> responseMasker;

    @Override
    public void log(HttpRequest request) {
        URI uri = request.uri();
        MDC.put(REQUEST_URI, uri.toString());
        MDC.put(REQUEST_URI_TRANSFORMED, uriTransformer.apply(uri.toString()));
        MDC.put(REQUEST_METHOD, request.method());
        log.info("sending-request:uri:{}:body:{}:headers:{}",
                uri,
                requestMasker.apply(extractBody(request)),
                request.headers().map()
        );
    }

    @Override
    public void log(HttpResponse<String> response) {
        MDC.put(REQUEST_STATUS, Integer.toString(response.statusCode()));
        log.info("received-response:status:{}:body:{}:headers:{}",
                response.statusCode(),
                responseMasker.apply(response.body()),
                response.headers().map()
        );
    }

    @Override
    public void logDuration(long duration) {
        MDC.put(REQUEST_DURATION, Long.toString(duration));
        log.info("client request took {} ms", duration);
    }

    @Override
    public void complete() {
        MDC.remove(REQUEST_URI);
        MDC.remove(REQUEST_URI_TRANSFORMED);
        MDC.remove(REQUEST_METHOD);
        MDC.remove(REQUEST_STATUS);
        MDC.remove(REQUEST_DURATION);
    }

    private static String extractBody(HttpRequest request) {
        return request.bodyPublisher()
                .map(ClientBodyLogger::toBody)
                .orElse("");
    }

    private static String toBody(HttpRequest.BodyPublisher publisher) {
        HttpResponse.BodySubscriber<String> subscriber = HttpResponse.BodySubscribers.ofString(UTF_8);
        publisher.subscribe(new BodyLoggingSubscriber(subscriber));
        return subscriber.getBody().toCompletableFuture().join();
    }

}
