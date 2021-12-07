package uk.co.idv.app.spring.info;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AppNameInfoContributorTest {

    private static final String APP_NAME = "my-app-name";

    private final InfoContributor contributor = new AppNameInfoContributor(APP_NAME);

    @Test
    void shouldPopulateAppNameOnInfo() {
        Info.Builder builder = new Info.Builder();

        contributor.contribute(builder);

        Info info = builder.build();
        assertThat(info.get("app")).isEqualTo(Map.of("name", APP_NAME));
    }

}
