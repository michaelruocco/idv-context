package uk.co.idv.context.adapter.client.logger;

import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Flow;

public class BodyLoggingSubscriber implements Flow.Subscriber<ByteBuffer> {

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
