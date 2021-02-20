package uk.co.idv.context.adapter.verification.client;

import lombok.Builder;
import uk.co.idv.context.adapter.verification.client.exception.ClientException;
import uk.co.idv.context.adapter.verification.client.logger.ClientLogger;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static uk.co.mruoc.duration.calculator.DurationCalculatorUtils.millisBetweenNowAnd;

@Builder
public class RequestExecutor {

    private final HttpClient client;
    private final ClientLogger clientLogger;

    public HttpResponse<String> execute(HttpRequest request) {
        try {
            clientLogger.log(request);
            HttpResponse<String> response = send(request);
            clientLogger.log(response);
            return response;
        } finally {
            clientLogger.complete();
        }
    }

    private HttpResponse<String> send(HttpRequest request) {
        Instant start = Instant.now();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ClientException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException(e);
        } finally {
            clientLogger.logDuration(millisBetweenNowAnd(start));
        }
    }

}
