package uk.co.idv.context.adapter.client;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.idv.context.adapter.client.logger.ClientLogger;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class RequestExecutorTest {

    private final HttpClient client = mock(HttpClient.class);
    private final ClientLogger clientLogger = mock(ClientLogger.class);

    private final RequestExecutor executor = RequestExecutor.builder()
            .client(client)
            .clientLogger(clientLogger)
            .build();

    @Test
    void shouldExecuteRequestSuccessfully() throws IOException, InterruptedException {
        HttpRequest request = mock(HttpRequest.class);
        HttpResponse<String> expectedResponse = mock(HttpResponse.class);
        given(client.send(request,  HttpResponse.BodyHandlers.ofString())).willReturn(expectedResponse);

        HttpResponse<String> response = executor.execute(request);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void shouldLogRequestAndResponseForSuccessfulRequest() throws IOException, InterruptedException {
        HttpRequest request = mock(HttpRequest.class);
        HttpResponse<String> expectedResponse = mock(HttpResponse.class);
        given(client.send(request,  HttpResponse.BodyHandlers.ofString())).willReturn(expectedResponse);

        executor.execute(request);

        InOrder inOrder = inOrder(clientLogger);
        inOrder.verify(clientLogger).log(request);
        inOrder.verify(clientLogger).logDuration(any(Long.class));
        inOrder.verify(clientLogger).log(expectedResponse);
        inOrder.verify(clientLogger).complete();
    }

    @Test
    void shouldThrowExceptionIfExecuteRequestThrowsIOException() throws IOException, InterruptedException {
        HttpRequest request = mock(HttpRequest.class);
        Exception cause = new IOException();
        given(client.send(request,  HttpResponse.BodyHandlers.ofString())).willThrow(cause);

        Throwable error = catchThrowable(() -> executor.execute(request));

        assertThat(error)
                .isInstanceOf(ClientException.class)
                .hasCause(cause);
    }

    @Test
    void shouldThrowExceptionIfExecuteRequestThrowsInterruptedException() throws IOException, InterruptedException {
        HttpRequest request = mock(HttpRequest.class);
        Exception cause = new InterruptedException();
        given(client.send(request,  HttpResponse.BodyHandlers.ofString())).willThrow(cause);

        Throwable error = catchThrowable(() -> executor.execute(request));

        assertThat(error)
                .isInstanceOf(ClientException.class)
                .hasCause(cause);
    }

    @Test
    void shouldLogRequestAndDurationOnRequestThatErrors() throws IOException, InterruptedException {
        HttpRequest request = mock(HttpRequest.class);
        given(client.send(request,  HttpResponse.BodyHandlers.ofString())).willThrow(new IOException());

        Throwable error = catchThrowable(() -> executor.execute(request));

        assertThat(error).isInstanceOf(ClientException.class);
        InOrder inOrder = inOrder(clientLogger);
        inOrder.verify(clientLogger).log(request);
        inOrder.verify(clientLogger).logDuration(any(Long.class));
        inOrder.verify(clientLogger).complete();
    }

}
