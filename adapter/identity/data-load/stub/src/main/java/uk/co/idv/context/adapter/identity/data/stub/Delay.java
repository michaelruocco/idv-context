package uk.co.idv.context.adapter.identity.data.stub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public class Delay {

    private final Duration duration;

    public void execute() {
        try {
            log.debug("sleeping for {}", duration);
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
