package uk.co.idv.app.spring;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import uk.co.idv.config.repository.dynamo.DynamoTableFactory;
import uk.co.idv.config.repository.dynamo.DynamoTables;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static uk.co.idv.app.spring.AppRunner.startAppIfNotRunning;

@Testcontainers
@Slf4j
public class ParallelIntegrationTest {

    private static final String ENVIRONMENT = "idv-local";

    @Container
    public static GenericContainer DYNAMO_DB = new GenericContainer<>("localstack/localstack")
            .withEnv("SERVICES", "dynamodb")
            .withEnv("DEFAULT_REGION", "eu-west-1")
            .withCopyFileToContainer(MountableFile.forHostPath("localstack/scripts/create-tables.sh"), "/docker-entrypoint-initaws.d/create-tables.sh")
            .withCopyFileToContainer(MountableFile.forHostPath("localstack/tables/identity.json"), "/opt/tables/identity.json")
            .withExposedPorts(4569)
            .withLogConsumer(new LogConsumer());

    @BeforeAll
    static void setUp() {
        await().pollDelay(Duration.ofSeconds(5))
                .pollInterval(Duration.ofSeconds(5))
                .until(dynamoIsRunning());
        DynamoTableFactory tableFactory = DynamoTableFactory.builder()
                .endpointUrl(getDynamoEndpointUri())
                .environment(ENVIRONMENT)
                .build();
        DynamoTables tables = tableFactory.build();
        tables.waitForIdentityTableToBeActive();
        setApplicationProperties();
        startAppIfNotRunning();
    }

    private static Callable<Boolean> dynamoIsRunning() {
        return () -> {
            boolean running = DYNAMO_DB.isRunning();
            log.info("checking dynamo is running {}", running);
            return running;
        };
    }

    private static void setApplicationProperties() {
        System.setProperty("aws.dynamo.db.endpoint.uri", getDynamoEndpointUri());
        System.setProperty("environment", ENVIRONMENT);
    }

    private static String getDynamoEndpointUri() {
        return String.format("http://%s:%s", DYNAMO_DB.getContainerIpAddress(), DYNAMO_DB.getMappedPort(4569));
    }

    @Test
    void identityMaintenanceTest() {
        String reportDir = "build/reports/karate";
        Results results = Runner.path(getFeaturePaths())
                .reportDir(reportDir)
                .parallel(5);

        assertThat(results.getFailCount())
                .withFailMessage(results.getErrorMessages())
                .isEqualTo(0);
    }

    private static String[] getFeaturePaths() {
        return new String[]{
                "classpath:identity",
                "classpath:eligibility"
        };
    }

}
