package uk.co.idv.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import static org.assertj.core.api.Assertions.assertThatCode;
import static uk.org.webcompere.systemstubs.resource.Resources.with;

@Testcontainers
@Slf4j
class SpringApplicationWithRedisTest {

    @Container
    public static final LocalMongo MONGO = new LocalMongo();

    @Container
    public static final LocalRedis REDIS = new LocalRedis();

    @BeforeAll
    static void setUp() {
        REDIS.waitForStartupToComplete();
        MONGO.waitForStartupToComplete();
    }

    @Test
    void applicationShouldStartWithRedis() {
        assertThatCode(this::startApplication).doesNotThrowAnyException();
    }

    private void startApplication() throws Exception {
        with(systemProperties()).execute(() -> SpringApplication.main(new String[0]));
    }

    private SystemProperties systemProperties() {
        return new SystemProperties()
                .set("redis.endpoint.uri", REDIS.getEndpointUri())
                .set("spring.data.mongodb.uri", MONGO.getConnectionString())
                .set("spring.profiles.active", "redis,simple-logging")
                .set("server.port", "0");
    }

}
