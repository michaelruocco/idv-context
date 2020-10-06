package uk.co.idv.app.spring.config.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.method.config.otp.OtpConfig;
import uk.co.idv.method.usecases.MethodBuilder;

@Configuration
public class SpringOtpConfig {

    @Bean
    public MethodBuilder otpConfig(ContextRepositoryConfig contextRepositoryConfig) {
        return OtpConfig.builder()
                .contextRepository(contextRepositoryConfig.contextRepository())
                .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
                .build()
                .otpBuilder();
    }

}
