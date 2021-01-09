package uk.co.idv.context.adapter.client.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BodyExtractorTest {

    @Test
    void shouldReturnExceptionIfPublisherThrowsException() {
        RuntimeException expectedCause = new RuntimeException("boom!");
        HttpRequest.BodyPublisher publisher = new FakeFailingBodyPublisher(expectedCause);

        Throwable error = catchThrowable(() -> BodyExtractor.extractBody(publisher));

        assertThat(error)
                .isInstanceOf(ExtractRequestBodyException.class)
                .hasCauseInstanceOf(ExecutionException.class)
                .hasMessage("java.util.concurrent.ExecutionException: java.lang.RuntimeException: boom!");
    }

    @RequiredArgsConstructor
    private static class FakeFailingBodyPublisher implements HttpRequest.BodyPublisher {

        private final RuntimeException error;

        @Override
        public long contentLength() {
            return 0;
        }

        @Override
        public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
            subscriber.onError(error);
        }

    }

}
