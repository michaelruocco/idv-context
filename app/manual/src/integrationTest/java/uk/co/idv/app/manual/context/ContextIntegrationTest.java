package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.config.ContextFacadeConfig;
import uk.co.idv.context.config.ContextServiceConfig;
import uk.co.idv.context.config.method.otp.ContextOtpConfig;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequestMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatusesMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.context.usecases.policy.NoContextPoliciesConfiguredException;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.time.Duration;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ContextIntegrationTest {

    private final ContextRepositoryConfig repositoryConfig = new InMemoryContextRepositoryConfig();

    private final SimSwapConfig simSwapConfig = SimSwapConfig.builder()
            .minDaysSinceSwap(5L)
            .acceptableStatuses(AcceptableSimSwapStatusesMother.onlySuccess())
            .timeout(Duration.ofSeconds(2))
            .build();

    private final StubSimSwapExecutorConfig simSwapExecutorConfig = StubSimSwapExecutorConfig.builder()
            .executorService(Executors.newCachedThreadPool())
            .simSwapConfig(simSwapConfig)
            .build();

    private final ContextOtpConfig otpConfig = ContextOtpConfig.builder()
            .simSwapExecutorConfig(simSwapExecutorConfig)
            .build();

    private final ContextServiceConfig serviceConfig = ContextServiceConfig.builder()
            .repositoryConfig(repositoryConfig)
            .otpConfig(otpConfig)
            .build();

    private final IdentityConfig identityConfig = IdentityConfig.builder()
            .build();

    private final ContextFacadeConfig contextConfig = ContextFacadeConfig.builder()
            .repositoryConfig(repositoryConfig)
            .serviceConfig(serviceConfig)
            .createEligibility(identityConfig.createEligibility())
            .build();

    private final ContextPolicyService policyService = contextConfig.policyService();
    private final ContextFacade contextFacade = contextConfig.contextFacade();

    @Test
    void shouldThrowExceptionIfNoContextPoliciesConfigured() {
        CreateContextRequest request = DefaultCreateContextRequestMother.build();

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(NoContextPoliciesConfiguredException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnRecordAttempt() {
        CreateContextRequest request = DefaultCreateContextRequestMother.build();
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(request.getChannelId()))
                .build();
        policyService.create(policy);


        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

}
