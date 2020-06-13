package uk.co.idv.context.adapter.stub.identity.find.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
@Data
public class Delay {

    private final Duration duration;

    public Delay(long millis) {
        this(Duration.ofMillis(millis));
    }

    public void execute() {
        try {
            log.debug("sleeping for {}", duration);
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
