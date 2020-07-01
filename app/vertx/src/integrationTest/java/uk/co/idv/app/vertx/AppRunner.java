package uk.co.idv.app.vertx;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class AppRunner {

    private static boolean running;

    public static void startAppIfNotRunning() {
        if (running) {
            return;
        }
        startApp();
        running = true;
    }

    private static void startApp() {
        Main.main(new String[0]);
        await().dontCatchUncaughtExceptions()
                .atMost(5, SECONDS)
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(8081));
    }

}
