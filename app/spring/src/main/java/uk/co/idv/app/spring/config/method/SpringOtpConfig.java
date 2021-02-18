package uk.co.idv.app.spring.config.method;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.context.adapter.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.otp.AppOtpConfig;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutorConfig;

@Configuration
public class SpringOtpConfig {

    @Profile("!test")
    @Bean
    public SimSwapExecutorConfig defaultOtpConfig() {
        return StubSimSwapExecutorConfig.build();
    }

    @Profile("test")
    @Bean
    public SimSwapExecutorConfig testOtpConfig() {
        return StubSimSwapExecutorConfig.withFixedDelay();
    }

    @Bean
    public AppMethodConfig otpConfig(SimSwapExecutorConfig simSwapExecutorConfig,
                                     AppAdapter appAdapter,
                                     RepositoryAdapter repositoryAdapter) {
        return AppOtpConfig.builder()
                .simSwapExecutorConfig(simSwapExecutorConfig)
                .clock(appAdapter.getClock())
                .uuidGenerator(appAdapter.getUuidGenerator())
                .contextRepository(repositoryAdapter.getContextRepository())
                .build();
    }

}
