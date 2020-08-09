package uk.co.idv.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

@Slf4j
public class LocalRedis extends GenericContainer<LocalRedis> {

    public LocalRedis() {
        super("redis:latest");
        withExposedPorts(6379);
        withLogConsumer(this::logInfo);
    }

    public void waitForStartupToComplete() {
        await().pollDelay(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .until(containerIsRunning());
    }

    public String getEndpointUri() {
        String ip = getContainerIpAddress();
        int port = getMappedPort(6379);
        return String.format("redis://%s:%s", ip, port);
    }

    private void logInfo(OutputFrame frame) {
        log.info(frame.getUtf8String());
    }

    private Callable<Boolean> containerIsRunning() {
        return () -> {
            boolean running = this.isRunning();
            log.info("checking redis container is running {}", running);
            return running;
        };
    }

}
