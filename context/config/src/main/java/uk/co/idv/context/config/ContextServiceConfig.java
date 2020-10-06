package uk.co.idv.context.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextService;
import uk.co.idv.context.usecases.context.CreateContextRequestConverter;
import uk.co.idv.context.usecases.context.method.CompositeMethodBuilder;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.context.usecases.context.sequence.SequenceBuilder;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.method.usecases.MethodBuilder;

import java.time.Clock;
import java.util.Collection;

@Builder
@Slf4j
public class ContextServiceConfig {

    private final ParentContextRepositoryConfig repositoryConfig;
    private final Collection<MethodBuilder> methodBuilders;

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    public ContextService contextService() {
        return ContextService.builder()
                .clock(clock)
                .repository(repositoryConfig.contextRepository())
                .requestConverter(serviceCreateContextRequestConverter())
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
        return new MethodsBuilder(new CompositeMethodBuilder(methodBuilders));
    }

    private CreateContextRequestConverter serviceCreateContextRequestConverter() {
        return new CreateContextRequestConverter(idGenerator);
    }

}
