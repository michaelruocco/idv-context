package uk.co.idv.app.vertx;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class ParallelIntegrationTest {

    @BeforeAll
    static void setUp() {
        Main.main(new String[0]);

        await().dontCatchUncaughtExceptions()
                .atMost(5, SECONDS)
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(8081));
    }

    @Test
    void identityMaintenanceTest() {
        String reportDir = "build/reports/karate";
        Results results = Runner.path("classpath:identity")
                .reportDir(reportDir)
                .parallel(5);

        assertThat(results.getFailCount())
                .withFailMessage(results.getErrorMessages())
                .isEqualTo(0);
    }

}
