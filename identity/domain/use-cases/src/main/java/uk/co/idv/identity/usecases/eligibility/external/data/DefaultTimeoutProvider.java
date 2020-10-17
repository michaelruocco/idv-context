package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class DefaultTimeoutProvider implements TimeoutProvider {

    private final Duration timeout;

    @Override
    public Duration getTimeout(String channelId) {
        return timeout;
    }

}
