package uk.co.idv.app.spring.config.method;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.push.AppPushNotificationConfig;

@Configuration
public class SpringPushNotificationConfig {

    @Bean
    public AppMethodConfig pushNotificationConfig() {
        return new AppPushNotificationConfig();
    }

}
