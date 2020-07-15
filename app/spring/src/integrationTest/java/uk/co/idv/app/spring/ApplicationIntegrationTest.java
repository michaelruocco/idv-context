package uk.co.idv.app.spring;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

public class ApplicationIntegrationTest {

    @Test
    @SetSystemProperty(key = "spring.profiles.active", value = "stubbed")
    @SetSystemProperty(key = "server.port", value = "0")
    void applicationShouldStartWithStubbedProfile() {
        Application.main(new String[0]);
    }

}
