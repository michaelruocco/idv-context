package uk.co.idv.app.vertx;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.app.vertx.AppRunner.startAppIfNotRunning;

public class ParallelIntegrationTest {

    @BeforeAll
    static void setUp() {
        startAppIfNotRunning();
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
