package uk.co.idv.app.spring.config.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.context.adapter.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.otp.AppOtpConfig;

@Configuration
public class SpringOtpConfig {

    @Bean
    public AppMethodConfig otpConfig(AppAdapter appAdapter,
                                     RepositoryAdapter repositoryAdapter) {
        return AppOtpConfig.builder()
                .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
                .clock(appAdapter.getClock())
                .idGenerator(appAdapter.getIdGenerator())
                .contextRepository(repositoryAdapter.getContextRepository())
                .build();
    }

}
