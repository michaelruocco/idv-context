package uk.co.idv.context.adapter.verification.client.logger;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Flow;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BodyLoggingSubscriberTest {

    private final HttpResponse.BodySubscriber<String> wrapped = mock(HttpResponse.BodySubscriber.class);

    private final BodyLoggingSubscriber subscriber = new BodyLoggingSubscriber(wrapped);

    @Test
    void shouldCallOnSubscribeOnWrappedSubscriber() {
        Flow.Subscription subscription = mock(Flow.Subscription.class);

        subscriber.onSubscribe(subscription);

        verify(wrapped).onSubscribe(subscription);
    }

    @Test
    void shouldCallOnNextOnWrappedSubscriber() {
        ByteBuffer buffer = mock(ByteBuffer.class);

        subscriber.onNext(buffer);

        verify(wrapped).onNext(List.of(buffer));
    }

    @Test
    void shouldCallOnErrorOnWrappedSubscriber() {
        Throwable throwable = mock(Throwable.class);

        subscriber.onError(throwable);

        verify(wrapped).onError(throwable);
    }

    @Test
    void shouldCallOnCompleteOnWrappedSubscriber() {
        subscriber.onComplete();

        verify(wrapped).onComplete();
    }

}
