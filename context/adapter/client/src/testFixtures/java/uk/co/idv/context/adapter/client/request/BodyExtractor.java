package uk.co.idv.context.adapter.client.request;

import uk.co.idv.context.adapter.client.logger.BodyLoggingSubscriber;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BodyExtractor {

    public static String extractBody(HttpRequest.BodyPublisher publisher) {
        try {
            HttpResponse.BodySubscriber<String> subscriber = HttpResponse.BodySubscribers.ofString(UTF_8);
            publisher.subscribe(new BodyLoggingSubscriber(subscriber));
            return subscriber.getBody().toCompletableFuture().get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ExtractRequestBodyException(e);
        } catch (ExecutionException e) {
            throw new ExtractRequestBodyException(e);
        }
    }

}
