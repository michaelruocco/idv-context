package uk.co.idv.app.spring;

import com.amazonaws.regions.Regions;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import uk.co.idv.context.adapter.dynamo.EnvironmentDynamoTablesFactory;
import uk.co.idv.context.adapter.dynamo.DynamoTables;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.testcontainers.utility.MountableFile.forHostPath;
import static uk.co.idv.identity.config.repository.dynamo.DynamoIdentityRepositoryConfig.IDENTITY_TABLE_NAME;
import static uk.co.idv.lockout.config.repository.dynamo.DynamoAttemptRepositoryConfig.ATTEMPT_TABLE_NAME;

@Slf4j
public class LocalAwsServices extends GenericContainer<LocalAwsServices> {

    private static final int PORT = 4566;

    private static DynamoTables dynamoTables;

    private final String environment;
    private final Regions region;

    public LocalAwsServices(Regions region, String environment) {
        super("localstack/localstack:latest");
        withEnv("SERVICES", "dynamodb");
        withEnv("DEFAULT_REGION", region.getName());
        withCopyFileToContainer(forHostPath("localstack/scripts/create-tables.sh"), "/docker-entrypoint-initaws.d/create-tables.sh");
        withCopyFileToContainer(forHostPath("localstack/tables/identity.json"), "/opt/tables/identity.json");
        withCopyFileToContainer(forHostPath("localstack/tables/attempt.json"), "/opt/tables/attempt.json");
        withExposedPorts(PORT);
        withLogConsumer(this::logInfo);
        this.region = region;
        this.environment = environment;
    }

    public void waitForStartupToComplete() {
        await().pollDelay(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .until(containerIsRunning());
    }

    public void waitForDynamoTablesToActive() {
        DynamoTables tables = getOrBuildDynamoTables();
        tables.waitForTablesToBeActive(
                IDENTITY_TABLE_NAME,
                ATTEMPT_TABLE_NAME
        );
    }

    public String getDynamoEndpointUri() {
        String ip = getContainerIpAddress();
        int port = getMappedPort(PORT);
        return String.format("http://%s:%s", ip, port);
    }

    private void logInfo(OutputFrame frame) {
        log.info(frame.getUtf8String());
    }

    private Callable<Boolean> containerIsRunning() {
        return () -> {
            boolean running = this.isRunning();
            log.info("checking aws service container is running {}", running);
            return running;
        };
    }

    private DynamoTables getOrBuildDynamoTables() {
        if (dynamoTables == null) {
            EnvironmentDynamoTablesFactory factory = EnvironmentDynamoTablesFactory.builder()
                    .endpointUrl(getDynamoEndpointUri())
                    .region(region.getName())
                    .environment(environment)
                    .build();
            dynamoTables = factory.build();
        }
        return dynamoTables;
    }

}
