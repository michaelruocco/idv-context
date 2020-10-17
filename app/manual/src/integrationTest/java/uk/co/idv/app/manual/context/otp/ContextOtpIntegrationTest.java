package uk.co.idv.app.manual.context.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.AppConfig;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.manual.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.common.usecases.id.NonRandomIdGenerator;
import uk.co.idv.context.adapter.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.otp.AppOtpConfig;
import uk.co.idv.method.entities.otp.GetOtpIfNextEligible;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodEligibilityIncomplete;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ContextOtpIntegrationTest {

    private final RepositoryAdapter repositoryAdapter = new InMemoryRepositoryAdapter();
    private final AppAdapter appAdapter = DefaultAppAdapter.builder()
            .repositoryAdapter(repositoryAdapter)
            .idGenerator(new NonRandomIdGenerator())
            .build();
    private final AppMethodConfig otpConfig = AppOtpConfig.builder()
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
            .clock(appAdapter.getClock())
            .idGenerator(appAdapter.getIdGenerator())
            .contextRepository(repositoryAdapter.getContextRepository())
            .build();
    private final MethodBuilders methodBuilders = new MethodBuilders(otpConfig.methodBuilder());
    private final AppConfig appConfig = new AppConfig(methodBuilders, repositoryAdapter, appAdapter);

    private final ContextConfig contextConfig = appConfig.getContextConfig();
    private final ContextPolicyService contextPolicyService = contextConfig.getPolicyService();
    private final ContextFacade contextFacade = contextConfig.getFacade();
    private final IdentityService identityService = appConfig.getIdentityConfig().identityService();
    private final LockoutPolicyService lockoutPolicyService = appConfig.getLockoutConfig().getPolicyService();


    @Test
    void shouldPopulateOtpMethodOnContextIfOtpPolicyConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(request.getChannelId()))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(OtpPolicyMother.build()))
                .build();
        contextPolicyService.create(policy);
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        Stream<Otp> otps = context.query(new GetOtpIfNextEligible());
        assertThat(otps).hasSize(1);
    }

    @Test
    void shouldReturnIncompleteDeliveryMethodOnCreateAndCompleteOnGetIfAsyncSimSwapOtpPolicyConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(request.getChannelId()))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(OtpPolicyMother.withSmsAsyncSimSwap()))
                .build();
        contextPolicyService.create(policy);
        givenContextPolicyExistsForChannel(request.getChannelId());
        PhoneNumber phoneNumber = PhoneNumberMother.delayedSimSwapMobileNumber();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(request.getAliases())
                .phoneNumbers(PhoneNumbersMother.with(phoneNumber))
                .emailAddresses(EmailAddressesMother.empty())
                .build();
        givenIdentityExists(identity);
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        UUID deliveryMethodId = UUID.fromString("446846e6-bf16-4da5-af5b-9ad4a240fe5d");
        assertThat(context.query(new DeliveryMethodEligibilityIncomplete(deliveryMethodId))).isTrue();

        DeliveryMethodEligibleAndComplete deliveryMethodEligibleAndComplete = DeliveryMethodEligibleAndComplete.builder()
                .contextFacade(contextFacade)
                .contextId(context.getId())
                .deliveryMethodId(deliveryMethodId)
                .build();

        await().pollDelay(Duration.ofSeconds(3))
                .pollInterval(Duration.ofMillis(250))
                .atMost(Duration.ofSeconds(4))
                .until(deliveryMethodEligibleAndComplete);

        assertThat(deliveryMethodEligibleAndComplete.isSuccessful()).isTrue();
    }

    private void givenContextPolicyExistsForChannel(String channelId) {
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(OtpPolicyMother.build()))
                .build();
        contextPolicyService.create(policy);
    }

    private void givenIdentityExistsForAliases(Aliases aliases) {
        givenIdentityExists(IdentityMother.withAliases(aliases));
    }

    private void givenIdentityExists(Identity identity) {
        identityService.update(identity);
    }

    private void givenLockoutPolicyExistsForChannel(String channelId) {
        LockoutPolicy policy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .build();
        lockoutPolicyService.create(policy);
    }

}
