package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.CreditCardNumberMother;
import uk.co.idv.identity.entities.alias.DebitCardNumberMother;
import uk.co.idv.identity.entities.identity.EmptyIdentityMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ContextMergeLockoutAttemptsTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldRecordAttemptsWhenVerificationsCompletedUnsuccessfully() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        harness.givenVerificationCompletedUnsuccessfully(CreateVerificationRequestMother.withContextId(context.getId()));
        harness.givenVerificationCompletedUnsuccessfully(CreateVerificationRequestMother.withContextId(context.getId()));

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        assertThat(state.getAttempts()).hasSize(2);
    }

    @Test
    void shouldMergeLockoutAttemptsFromTwoDifferentIdentitiesWhenMerged() {
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Context context1 = recordUnsuccessfulAttemptForIdentityWithAlias(creditCardNumber);

        Alias debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Context context2 = recordUnsuccessfulAttemptForIdentityWithAlias(debitCardNumber);

        assertThat(context1.getIdvId()).isNotEqualTo(context2.getIdvId());

        Identity mergeIdentity = EmptyIdentityMother.builder()
                .aliases(AliasesMother.with(creditCardNumber, debitCardNumber))
                .build();
        Identity mergedIdentity = application.update(mergeIdentity);

        LockoutState state = loadLockoutState(context1, mergedIdentity.getIdvId());

        assertThat(state.getAttempts()).hasSize(2);

        Throwable context1Error = catchThrowable(() -> loadLockoutState(context1));
        assertThat(context1Error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessageContaining(context1.getIdvId().format());

        Throwable context2Error = catchThrowable(() -> loadLockoutState(context2));
        assertThat(context2Error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessageContaining(context2.getIdvId().format());
    }

    private Context recordUnsuccessfulAttemptForIdentityWithAlias(Alias alias) {
        MethodPolicy methodPolicy = FakeMethodPolicyMother.build();
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.builder()
                .aliases(AliasesMother.with(alias))
                .build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId(), methodPolicy);
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);
        CreateVerificationRequest request = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName(methodPolicy.getName())
                .build();
        harness.givenVerificationCompletedUnsuccessfully(request);
        return context;
    }

    private LockoutState loadLockoutState(Context context) {
        return loadLockoutState(context, context.getIdvId());
    }

    private LockoutState loadLockoutState(Context context, Alias alias) {
        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(context.getActivityName())
                .channelId(context.getChannelId())
                .aliases(AliasesMother.with(alias))
                .build();
        return application.loadLockoutState(lockoutRequest);
    }

}
