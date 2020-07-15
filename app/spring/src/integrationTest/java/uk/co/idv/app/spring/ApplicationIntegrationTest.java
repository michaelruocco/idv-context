package uk.co.idv.app.spring;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

public class ApplicationIntegrationTest {

    @Test
    @SetSystemProperty(key = "spring.profiles.active", value = "stubbed")
    void applicationShouldStartWithStubbedProfile() {
        Application.main(new String[0]);
    }

}
