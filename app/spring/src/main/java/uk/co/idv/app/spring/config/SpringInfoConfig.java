package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.spring.info.SecureProperties;
import uk.co.idv.app.spring.info.SystemPropertyInfoContributor;

@Configuration
public class SpringInfoConfig {

    @Bean
    public SystemPropertyInfoContributor profileContributor() {
        return new SystemPropertyInfoContributor(new SecureProperties());
    }

}
