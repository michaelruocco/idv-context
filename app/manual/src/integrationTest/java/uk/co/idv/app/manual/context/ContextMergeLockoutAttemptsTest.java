package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;
import uk.co.idv.context.usecases.context.result.NotNextMethodException;
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
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ContextMergeLockoutAttemptsTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldPopulateResultOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(ResultMother.withMethodName("fake-method"))
                .build();
        Context updated = application.record(recordRequest);

        assertThat(updated.isComplete()).isTrue();
    }

    @Test
    void shouldThrowExceptionIfResultMethodIsNotOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.withMethodName("another-method");
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        Throwable error = catchThrowable(() -> application.record(recordRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfAttemptToPopulateResultOnContextThatIsAlreadyComplete() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);
        Result result = ResultMother.withMethodName("fake-method");
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);

        Throwable error = catchThrowable(() -> application.record(recordRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldRecordAttemptsWhenResultRecorded() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);
        application.record(recordRequest);

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
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.builder()
                .aliases(AliasesMother.with(alias))
                .build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);
        harness.givenUnsuccessfulResultRecorded(context.getId());
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
