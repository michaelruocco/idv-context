package uk.co.idv.app.manual.context.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.lockout.LockoutConfigBuilder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.NonRandomIdGenerator;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.config.ContextFacadeConfig;
import uk.co.idv.context.config.ContextServiceConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.identity.config.IdentityConfig;
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
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static uk.co.idv.context.entities.context.method.query.MethodQueryFactory.incompleteAndEligible;
import static uk.co.idv.context.entities.context.method.query.MethodQueryFactory.methodOfType;

class ContextOtpIntegrationTest {

    private final IdGenerator idGenerator = new NonRandomIdGenerator();

    private final ContextServiceConfig serviceConfig = ContextServiceConfig.builder()
            .repositoryConfig(new InMemoryContextRepositoryConfig())
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
            .idGenerator(idGenerator)
            .build();

    private final IdentityConfig identityConfig = IdentityConfig.builder()
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

        Stream<Otp> otp = context.find(incompleteAndEligible(Otp.class));
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
        Optional<DeliveryMethod> deliveryMethod = context.find(methodOfType(Otp.class))
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

        await().atMost(Duration.ofSeconds(4))
                .pollDelay(Duration.ofMillis(500))
                .until(deliveryMethodEligibleAndComplete);

        assertThat(deliveryMethodEligibleAndComplete.isSuccessful()).isTrue();
    }

    private void givenContextPolicyExistsForChannel(String channelId) {
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
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
