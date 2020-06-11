package uk.co.idv.context.usecases.identity.data.stubbed;

import java.time.Duration;

public class Sleeper {

    public void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
