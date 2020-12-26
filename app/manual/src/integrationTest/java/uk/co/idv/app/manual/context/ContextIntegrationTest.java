package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.EligibleMethodsContext;
import uk.co.idv.context.entities.context.EligibleMethodsContextRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.usecases.context.ContextExpiredException;
import uk.co.idv.context.usecases.context.ContextNotFoundException;
import uk.co.idv.context.usecases.policy.NoContextPoliciesConfiguredException;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredException;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ContextIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldThrowExceptionIfNoContextPoliciesConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();

        Throwable error = catchThrowable(() -> application.create(request));

        assertThat(error).isInstanceOf(NoContextPoliciesConfiguredException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistWhenCreatingContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());

        Throwable error = catchThrowable(() -> application.create(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfLockoutPolicyDoesNotExistingWhenCreatingContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());

        Throwable error = catchThrowable(() -> application.create(request));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldPopulateIdOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        assertThat(context.getId()).isEqualTo(UUID.fromString("0ec36d6b-145b-4c1e-9201-1f47a8eec9a5"));
    }

    @Test
    void shouldPopulateCreatedTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        assertThat(context.getCreated()).isEqualTo(harness.now());
    }

    @Test
    void shouldPopulateExpiryTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        Duration buffer = Duration.ofMinutes(1);
        Instant expectedExpiry = harness.now().plus(context.getDuration()).plus(buffer);
        assertThat(context.getExpiry()).isEqualTo(expectedExpiry);
    }

    @Test
    void shouldPopulateChannelOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        assertThat(context.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldPopulateActivityOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        assertThat(context.getActivity()).isEqualTo(request.getActivity());
    }

    @Test
    void shouldPopulateIdentityOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        Identity expectedIdentity = harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        assertThat(context.getIdentity()).usingRecursiveComparison().isEqualTo(expectedIdentity);
    }

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        UUID id = UUID.randomUUID();

        Throwable error = catchThrowable(() -> application.findContext(id));

        assertThat(error)
                .isInstanceOf(ContextNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnContextIfFound() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = application.create(request);

        Context loaded = application.findContext(created.getId());

        assertThat(loaded).isEqualTo(created);
    }

    @Test
    void shouldThrowExceptionIfContextHasExpired() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = application.create(request);
        UUID id = created.getId();
        harness.fastForwardTimeBy(created.getDuration().plusMinutes(1).plusMillis(1));

        ContextExpiredException error = catchThrowableOfType(
                () -> application.findContext(id),
                ContextExpiredException.class);

        assertThat(error.getId()).isEqualTo(id);
        assertThat(error.getExpiry()).isEqualTo(created.getExpiry());
    }

    @Test
    void shouldReturnEligibleMethodsContext() {
        MethodPolicy policy = FakeMethodPolicyMother.build();
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(request.getChannelId(), policy);
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = application.create(request);
        EligibleMethodsContextRequest eligibleMethodsRequest = EligibleMethodsContextRequest.builder()
                .contextId(created.getId())
                .methodName(policy.getName())
                .build();

        EligibleMethodsContext context = application.findEligibleMethodsContext(eligibleMethodsRequest);

        assertThat(context.getId()).isEqualTo(created.getId());
        assertThat(context.getActivity()).isEqualTo(created.getActivity());
        assertThat(context.getMethods()).isEqualTo(created.getNextMethods(policy.getName()));
        assertThat(context.isProtectSensitiveData()).isEqualTo(created.isProtectSensitiveData());
    }

}
