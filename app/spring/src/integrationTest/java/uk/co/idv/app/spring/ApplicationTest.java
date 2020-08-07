package uk.co.idv.app.spring;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static org.assertj.core.api.Assertions.assertThatCode;

class ApplicationTest {

    @Test
    @SetSystemProperty(key = "spring.profiles.active", value = "stubbed")
    @SetSystemProperty(key = "server.port", value = "0")
    void applicationShouldStartWithStubbedProfile() {
        assertThatCode(() -> Application.main(new String[0])).doesNotThrowAnyException();
    }

}
