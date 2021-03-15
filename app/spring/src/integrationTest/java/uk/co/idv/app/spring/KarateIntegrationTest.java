package uk.co.idv.app.spring;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;
import static uk.co.idv.app.spring.AppRunner.startApp;
import static uk.co.idv.app.spring.AppRunner.waitForAppStartupToComplete;

@Testcontainers
@Slf4j
class KarateIntegrationTest {

    private static final String ENVIRONMENT = "idv-local";

    private static final int THREAD_COUNT = 4;

    @Container
    public static final LocalMongo MONGO = new LocalMongo();

    @BeforeAll
    static void setUp() {
        setUpMongo();
        setUpApp();
    }

    @Test
    void runParallelFeatures() {
        String reportDir = "build/reports/karate";
        Results results = Runner.path(getFeaturePaths())
                .reportDir(reportDir)
                .tags("~@sequential")
                .parallel(THREAD_COUNT);

        assertThat(results.getFailCount())
                .withFailMessage(results.getErrorMessages())
                .isZero();
    }

    @Karate.Test
    Karate runSequentialFeatures() {
        return Karate.run(getFeaturePaths()).tags("@sequential");
    }

    private static String[] getFeaturePaths() {
        return new String[]{
                "classpath:identity",
                "classpath:eligibility",
                "classpath:lockout",
                "classpath:context",
                "classpath:actuator"
        };
    }

    private static void setUpMongo() {
        MONGO.waitForStartupToComplete();
    }

    private static void setUpApp() {
        int port = findAvailableTcpPort();
        setApplicationProperties(port);
        startApp();
        waitForAppStartupToComplete(port);
    }

    private static void setApplicationProperties(int serverPort) {
        System.setProperty("environment", ENVIRONMENT);
        System.setProperty("server.port", Integer.toString(serverPort));
        System.setProperty("spring.data.mongodb.uri", MONGO.getConnectionString());
        System.setProperty("response.filtering.enabled", "true");
        System.setProperty("spring.profiles.active", "simple-logging,test");
    }

}
