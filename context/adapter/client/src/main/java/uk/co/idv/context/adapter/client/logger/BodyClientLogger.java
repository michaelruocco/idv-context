package uk.co.idv.context.adapter.client.logger;

import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Flow;

@Slf4j
public class BodyClientLogger implements ClientLogger {

    @Override
    public void log(HttpRequest request) {
        log.info("sending-request:uri:{}:body:{}:headers:{}",
                request.uri(),
                extractBody(request),
                request.headers().map()
        );
    }

    @Override
    public void log(HttpResponse<String> response) {
        log.info("received-response:status:{}:body:{}:headers:{}",
                response.statusCode(),
                response.body(),
                response.headers().map()
        );
    }

    private static String extractBody(HttpRequest request) {
        return request.bodyPublisher().map(publisher -> {
            HttpResponse.BodySubscriber<String> subscriber = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);
            publisher.subscribe(new BodyClientLogger.BodyLoggingSubscriber(subscriber));
            return subscriber.getBody().toCompletableFuture().join();
        }).orElse("");
    }

    private static final class BodyLoggingSubscriber implements Flow.Subscriber<ByteBuffer> {

        private final HttpResponse.BodySubscriber<String> wrapped;

        public BodyLoggingSubscriber(HttpResponse.BodySubscriber<String> wrapped) {
            this.wrapped = wrapped;
        }
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            wrapped.onSubscribe(subscription);
        }

        @Override
        public void onNext(ByteBuffer item) { wrapped.onNext(List.of(item)); }

        @Override
        public void onError(Throwable throwable) { wrapped.onError(throwable); }

        @Override
        public void onComplete() { wrapped.onComplete(); }

    }

}
