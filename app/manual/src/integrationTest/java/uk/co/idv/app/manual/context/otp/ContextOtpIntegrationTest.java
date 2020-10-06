package uk.co.idv.app.manual.context.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.lockout.LockoutConfigBuilder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.NonRandomIdGenerator;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.config.ContextFacadeConfig;
import uk.co.idv.context.config.ContextServiceConfig;
import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.method.config.otp.OtpConfig;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ContextOtpIntegrationTest {

    private final IdGenerator idGenerator = new NonRandomIdGenerator();

    private final ParentContextRepositoryConfig contextRepositoryConfig = new InMemoryContextRepositoryConfig();

    private final OtpConfig otpConfig = OtpConfig.builder()
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
            .idGenerator(idGenerator)
            .contextRepository(contextRepositoryConfig.contextRepository())
            .build();

    private final ContextServiceConfig serviceConfig = ContextServiceConfig.builder()
            .repositoryConfig(contextRepositoryConfig)
            .idGenerator(idGenerator)
            .methodBuilders(Collections.singleton(otpConfig.otpBuilder()))
            .build();

    private final DefaultIdentityConfig identityConfig = DefaultIdentityConfig.builder()
            .build();

    private final LockoutConfig lockoutConfig = LockoutConfigBuilder.builder()
            .identityConfig(identityConfig)
            .build().build();

    private final ContextFacadeConfig contextConfig = ContextFacadeConfig.builder()
            .serviceConfig(serviceConfig)
            .lockoutService(lockoutConfig.lockoutService())
            .createEligibility(identityConfig.createEligibility())
            .build();

    private final ContextPolicyService contextPolicyService = serviceConfig.policyService();
    private final IdentityService identityService = identityConfig.identityService();
    private final LockoutPolicyService lockoutPolicyService = lockoutConfig.policyService();

    private final ContextFacade contextFacade = contextConfig.contextFacade();

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

        Methods methods = context.getNextEligibleIncompleteMethods("one-time-passcode");
        Stream<Otp> otp = methods.streamAsType(Otp.class);
        assertThat(otp).isNotEmpty();
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

        UUID deliveryMethodId = UUID.fromString("85bbb05a-3cf8-45e5-bae8-430503164c3b");
        Methods methods = context.getNextMethods("one-time-passcode");
        Optional<DeliveryMethod> deliveryMethod = methods.streamAsType(Otp.class)
                .map(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .flatMap(Optional::stream)
                .findFirst();
        assertThat(deliveryMethod).isPresent();
        assertThat(deliveryMethod.get().isEligibilityComplete()).isFalse();

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
