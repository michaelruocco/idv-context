package uk.co.idv.app.spring.config.lockout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.manual.AppConfig;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.usecases.LockoutFacade;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

@Configuration
public class SpringLockoutDomainConfig {

    @Bean
    public LockoutConfig lockoutConfig(AppConfig appConfig) {
        return appConfig.getLockoutConfig();
    }

    @Bean
    public LockoutFacade lockoutFacade(LockoutConfig lockoutConfig) {
        return lockoutConfig.getFacade();
    }

    @Bean
    public LockoutPolicyService lockoutPolicyService(LockoutConfig lockoutConfig) {
        return lockoutConfig.getPolicyService();
    }

    @Bean
    public ErrorHandler lockoutErrorHandler(LockoutConfig lockoutConfig) {
        return lockoutConfig.getErrorHandler();
    }

}
