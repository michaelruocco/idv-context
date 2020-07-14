package uk.co.idv.app.spring;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
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
        int port = getFreePort();
        System.setProperty("server.port", Integer.toString(port));
        Application.main(new String[0]);
        await().dontCatchUncaughtExceptions()
                .atMost(5, SECONDS)
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(port));
    }

    private static int getFreePort() {
        try {
            try (ServerSocket socket = new ServerSocket(0)) {
                return socket.getLocalPort();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
