package uk.co.idv.app.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.spring.info.AppNameInfoContributor;
import uk.co.idv.app.spring.info.SecureProperties;
import uk.co.idv.app.spring.info.SystemPropertyInfoContributor;

@Configuration
public class SpringInfoConfig {

    @Bean
    public InfoContributor appNameInfoContributor(@Value("${spring.application.name}") String appName) {
        return new AppNameInfoContributor(appName);
    }

    @Bean
    public InfoContributor systemPropertyInfoContributor() {
        return new SystemPropertyInfoContributor(new SecureProperties());
    }

}
