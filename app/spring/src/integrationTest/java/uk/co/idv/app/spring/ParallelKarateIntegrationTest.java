package uk.co.idv.app.spring;

import com.amazonaws.regions.Regions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
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
class ParallelKarateIntegrationTest {

    private static final Regions REGION = Regions.EU_WEST_1;
    private static final String ENVIRONMENT = "idv-local";

    private static final int THREAD_COUNT = 2;

    @Container
    public static final LocalAwsServices AWS_SERVICES = new LocalAwsServices(REGION, ENVIRONMENT);

    @Container
    public static final LocalRedis REDIS = new LocalRedis();

    @BeforeAll
    static void setUp() {
        setUpAws();
        setUpRedis();
        setUpApp();
    }

    @Test
    void runFeatureTests() {
        String reportDir = "build/reports/karate";
        Results results = Runner.path(getFeaturePaths())
                .reportDir(reportDir)
                .parallel(THREAD_COUNT);

        assertThat(results.getFailCount())
                .withFailMessage(results.getErrorMessages())
                .isZero();
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

    private static void setUpAws() {
        AWS_SERVICES.waitForStartupToComplete();
        AWS_SERVICES.waitForDynamoTablesToActive();
    }

    private static void setUpRedis() {
        REDIS.waitForStartupToComplete();
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
        System.setProperty("aws.dynamo.db.endpoint.uri", AWS_SERVICES.getDynamoEndpointUri());
        System.setProperty("redis.endpoint.uri", REDIS.getEndpointUri());
    }

}
