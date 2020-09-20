package uk.co.idv.context.config;

import lombok.Builder;
import uk.co.idv.context.config.method.otp.ContextOtpConfig;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextService;
import uk.co.idv.context.usecases.context.method.CompositeMethodBuilder;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.context.usecases.context.sequence.SequenceBuilder;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;
import uk.co.idv.context.usecases.policy.ContextPolicyService;

@Builder
public class ContextServiceConfig {

    private final ContextRepositoryConfig repositoryConfig;

    @Builder.Default
    private final ContextOtpConfig otpConfig = ContextOtpConfig.builder().build();

    public ContextService contextService() {
        return ContextService.builder()
                .repository(repositoryConfig.contextRepository())
                .sequencesBuilder(sequencesBuilder())
                .build();
    }

    public ContextPolicyService policyService() {
        return new ContextPolicyService(repositoryConfig.policyRepository());
    }

    private SequencesBuilder sequencesBuilder() {
        return new SequencesBuilder(new SequenceBuilder(methodsBuilder()));
    }

    private MethodsBuilder methodsBuilder() {
        return new MethodsBuilder(new CompositeMethodBuilder(
                otpConfig.otpBuilder()
        ));
    }

}
