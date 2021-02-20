package uk.co.idv.context.adapter.verification.client.logger;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.UnaryOperator;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Builder
public class BodyLoggingClientLogger implements ClientLogger {

    private final MdcPopulator mdcPopulator;
    private final UnaryOperator<String> requestMasker;
    private final UnaryOperator<String> responseMasker;

    @Override
    public void log(HttpRequest request) {
        mdcPopulator.populate(request);
        log.info("sending-request:uri:{}:headers:{}:body:{}",
                request.uri(),
                request.headers().map(),
                requestMasker.apply(extractBody(request))
        );
    }

    @Override
    public void log(HttpResponse<String> response) {
        mdcPopulator.populate(response);
        log.info("received-response:status:{}:headers:{}:body:{}",
                response.statusCode(),
                response.headers().map(),
                responseMasker.apply(response.body())
        );
    }

    @Override
    public void logDuration(long duration) {
        mdcPopulator.populateDuration(duration);
        log.info("client request took {} ms", duration);
    }

    @Override
    public void complete() {
        mdcPopulator.remove();
    }

    private static String extractBody(HttpRequest request) {
        return request.bodyPublisher()
                .map(BodyLoggingClientLogger::toBody)
                .orElse("");
    }

    private static String toBody(HttpRequest.BodyPublisher publisher) {
        HttpResponse.BodySubscriber<String> subscriber = HttpResponse.BodySubscribers.ofString(UTF_8);
        publisher.subscribe(new BodyLoggingSubscriber(subscriber));
        return subscriber.getBody().toCompletableFuture().join();
    }

}
