package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.lockout.LockoutConfigBuilder;
import uk.co.idv.context.config.ContextFacadeConfig;
import uk.co.idv.context.config.ContextServiceConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.context.usecases.policy.NoContextPoliciesConfiguredException;
import uk.co.idv.identity.config.IdentityConfig;
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
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

 class ContextIntegrationTest {

    private final ContextServiceConfig serviceConfig = ContextServiceConfig.builder()
            .repositoryConfig(new InMemoryContextRepositoryConfig())
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

        assertThat(context.getId()).isNotNull();
    }

    @Test
    void shouldPopulateCreatedTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getCreated()).isNotNull();
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

        Optional<Otp> otp = context.findNextIncompleteEligibleOtp();
        assertThat(otp).isPresent();
    }

    private void givenContextPolicyExistsForChannel(String channelId) {
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .build();
        contextPolicyService.create(policy);
    }

    private Identity givenIdentityExistsForAliases(Aliases aliases) {
        return identityService.update(IdentityMother.withAliases(aliases));
    }

    private void givenLockoutPolicyExistsForChannel(String channelId) {
        LockoutPolicy policy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .build();
        lockoutPolicyService.create(policy);
    }

}
