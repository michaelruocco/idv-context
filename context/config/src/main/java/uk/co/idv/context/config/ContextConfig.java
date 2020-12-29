package uk.co.idv.context.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.protector.ContextDataProtector;
import uk.co.idv.common.adapter.protector.DefaultContextDataProtector;
import uk.co.idv.common.adapter.protector.DataProtector;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.adapter.json.error.handler.ContextErrorHandler;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.ContextService;
import uk.co.idv.context.usecases.context.CreateContext;
import uk.co.idv.context.usecases.context.CreateContextRequestConverter;
import uk.co.idv.context.usecases.context.VerificationService;
import uk.co.idv.context.usecases.context.FindContext;
import uk.co.idv.context.usecases.context.MdcPopulator;
import uk.co.idv.context.usecases.context.event.create.CompositeContextCreatedHandler;
import uk.co.idv.context.usecases.context.event.create.ContextCreatedHandler;
import uk.co.idv.context.usecases.context.event.create.ExpiryContextCreatedHandler;
import uk.co.idv.context.usecases.context.event.create.LoggingContextCreatedHandler;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;
import uk.co.idv.context.usecases.context.method.CompositeMethodBuilder;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.context.usecases.context.result.ContextResultUpdater;
import uk.co.idv.context.usecases.context.result.ResultService;
import uk.co.idv.context.usecases.context.sequence.SequenceBuilder;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.protect.mask.EmailAddressMasker;
import uk.co.idv.identity.usecases.protect.mask.PhoneNumberMasker;
import uk.co.idv.lockout.usecases.LockoutService;
import uk.co.idv.method.config.AppMethodConfigs;

import java.time.Clock;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;

@Builder
@Slf4j
public class ContextConfig {

    private final Clock clock;
    private final IdGenerator idGenerator;
    private final AppMethodConfigs appMethodConfigs;
    private final ContextPolicyRepository policyRepository;
    private final ContextRepository contextRepository;
    private final CreateEligibility createEligibility;
    private final LockoutService lockoutService;

    public ContextFacade getFacade() {
        return ContextFacade.builder()
                .identityLoader(identityLoader())
                .contextService(contextService())
                .resultService(resultService())
                .verificationService(verificationService())
                .build();
    }

    public ContextPoliciesPopulator policiesPopulator() {
        return new ContextPoliciesPopulator(policyService());
    }

    public ContextPolicyService policyService() {
        return new ContextPolicyService(policyRepository);
    }

    public ContextErrorHandler errorHandler() {
        return new ContextErrorHandler();
    }

    private ContextService contextService() {
        return ContextService.builder()
                .createContext(createContext())
                .findContext(findContext())
                .build();
    }

    private ResultService resultService() {
        return ResultService.builder()
                .lockoutService(lockoutService())
                .repository(contextRepository)
                .resultUpdater(new ContextResultUpdater())
                .build();
    }

    private VerificationService verificationService() {
        return VerificationService.builder()
                .contextService(contextService())
                .idGenerator(idGenerator)
                .clock(clock)
                .build();
    }

    private IdentityLoader identityLoader() {
        return IdentityLoader.builder()
                .createEligibility(createEligibility)
                .policyService(policyService())
                .build();
    }

    private CreateContext createContext() {
        return CreateContext.builder()
                .clock(clock)
                .lockoutService(lockoutService())
                .repository(contextRepository)
                .requestConverter(serviceCreateContextRequestConverter())
                .sequencesBuilder(sequencesBuilder())
                .createdHandler(contextCreated())
                .protector(contextDataProtector())
                .build();
    }

    private ContextDataProtector contextDataProtector() {
        return DefaultContextDataProtector.builder()
                .dataProtectors(dataProtectors())
                .methodProtector(appMethodConfigs.methodProtector())
                .build();
    }

    private Collection<DataProtector<?>> dataProtectors() {
        return Arrays.asList(
                new DataProtector<>(EmailAddress.class, new EmailAddressMasker()),
                new DataProtector<>(PhoneNumber.class, new PhoneNumberMasker())
        );
    }

    private FindContext findContext() {
        return FindContext.builder()
                .clock(clock)
                .lockoutService(lockoutService())
                .repository(contextRepository)
                .mdcPopulator(mdcPopulator())
                .build();
    }

    private SequencesBuilder sequencesBuilder() {
        return new SequencesBuilder(new SequenceBuilder(methodsBuilder()));
    }

    private MethodsBuilder methodsBuilder() {
        return new MethodsBuilder(new CompositeMethodBuilder(appMethodConfigs.methodBuilders()));
    }

    private CreateContextRequestConverter serviceCreateContextRequestConverter() {
        return new CreateContextRequestConverter(idGenerator);
    }

    private ContextLockoutService lockoutService() {
        return ContextLockoutService.builder()
                .lockoutService(lockoutService)
                .clock(clock)
                .build();
    }

    private ContextCreatedHandler contextCreated() {
        return new CompositeContextCreatedHandler(
                loggingContextCreatedHandler(),
                expiryContextCreatedHandler()
        );
    }

    private ExpiryContextCreatedHandler expiryContextCreatedHandler() {
        return ExpiryContextCreatedHandler.builder()
                .clock(clock)
                .executor(Executors.newScheduledThreadPool(contextExpiryThreadPoolSize()))
                .build();
    }

    private LoggingContextCreatedHandler loggingContextCreatedHandler() {
        return new LoggingContextCreatedHandler(mdcPopulator());
    }

    private MdcPopulator mdcPopulator() {
        return new MdcPopulator();
    }

    private static int contextExpiryThreadPoolSize() {
        String key = "context.expiry.thread.pool.size";
        int size = Integer.parseInt(System.getProperty(key, "100"));
        log.info("loaded {} value {}", key, size);
        return size;
    }

}
