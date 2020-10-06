package uk.co.idv.app.manual.context;

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
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.usecases.context.ContextExpiredException;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.context.ContextNotFoundException;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.context.usecases.policy.NoContextPoliciesConfiguredException;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredException;
import uk.co.idv.method.config.otp.OtpConfig;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ContextIntegrationTest {

    private static final Instant NOW = Instant.parse("2020-10-06T21:00:00.000Z");

    private final UpdatableClock clock = new UpdatableClock(NOW);
    private final IdGenerator idGenerator = new NonRandomIdGenerator();

    private final ParentContextRepositoryConfig contextRepositoryConfig = new InMemoryContextRepositoryConfig();

    //TODO replace with fake method config/build builder
    private final OtpConfig otpConfig = OtpConfig.builder()
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
            .idGenerator(idGenerator)
            .clock(clock)
            .contextRepository(contextRepositoryConfig.contextRepository())
            .build();

    private final ContextServiceConfig serviceConfig = ContextServiceConfig.builder()
            .repositoryConfig(contextRepositoryConfig)
            .idGenerator(idGenerator)
            .clock(clock)
            .methodBuilders(Collections.singleton(otpConfig.otpBuilder()))
            .build();
    private final DefaultIdentityConfig identityConfig = DefaultIdentityConfig.builder()
            .build();

    private final LockoutConfig lockoutConfig = LockoutConfigBuilder.builder()
            .identityConfig(identityConfig)
            .build().build();

    private final ContextFacadeConfig contextConfig = ContextFacadeConfig.builder()
            .clock(clock)
            .serviceConfig(serviceConfig)
            .lockoutService(lockoutConfig.lockoutService())
            .createEligibility(identityConfig.createEligibility())
            .build();

    private final ContextPolicyService contextPolicyService = serviceConfig.policyService();
    private final IdentityService identityService = identityConfig.identityService();
    private final LockoutPolicyService lockoutPolicyService = lockoutConfig.policyService();

    private final ContextFacade contextFacade = contextConfig.contextFacade();

    @Test
    void shouldThrowExceptionIfNoContextPoliciesConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(NoContextPoliciesConfiguredException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistWhenCreatingContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfLockoutPolicyDoesNotExistingWhenCreatingContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldPopulateIdOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getId()).isEqualTo(UUID.fromString("76c9ec3b-b7aa-41ae-8066-796856e71e65"));
    }

    @Test
    void shouldPopulateCreatedTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateExpiryTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        Duration buffer = Duration.ofMinutes(1);
        Instant expectedExpiry = NOW.plus(context.getDuration()).plus(buffer);
        assertThat(context.getExpiry()).isEqualTo(expectedExpiry);
    }

    @Test
    void shouldPopulateChannelOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldPopulateActivityOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getActivity()).isEqualTo(request.getActivity());
    }

    @Test
    void shouldPopulateIdentityOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        Identity expectedIdentity = givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getIdentity()).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        UUID id = UUID.randomUUID();

        Throwable error = catchThrowable(() -> contextFacade.find(id));

        assertThat(error)
                .isInstanceOf(ContextNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnContextIfFound() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = contextFacade.create(request);

        Context loaded = contextFacade.find(created.getId());

        assertThat(loaded).isEqualTo(created);
    }

    @Test
    void shouldThrowExceptionIfContextHasExpired() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = contextFacade.create(request);
        UUID id = created.getId();
        clock.plus(created.getDuration().plusMinutes(1).plusMillis(1));

        ContextExpiredException error = catchThrowableOfType(
                () -> contextFacade.find(id),
                ContextExpiredException.class);

        assertThat(error.getId()).isEqualTo(id);
        assertThat(error.getExpiry()).isEqualTo(created.getExpiry());
    }

    private void givenContextPolicyExistsForChannel(String channelId) {
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(OtpPolicyMother.build()))
                .build();
        contextPolicyService.create(policy);
    }

    private Identity givenIdentityExistsForAliases(Aliases aliases) {
        return givenIdentityExists(IdentityMother.withAliases(aliases));
    }

    private Identity givenIdentityExists(Identity identity) {
        return identityService.update(identity);
    }

    private void givenLockoutPolicyExistsForChannel(String channelId) {
        LockoutPolicy policy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .build();
        lockoutPolicyService.create(policy);
    }

}
