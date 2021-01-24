package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.verification.CompleteVerificationRequest;
import uk.co.idv.context.entities.verification.CompleteVerificationResponse;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.Verifications;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.function.UnaryOperator;

@Builder(toBuilder = true)
@Data
@Slf4j
public class Context {

    private final UUID id;
    private final Instant created;
    private final Instant expiry;
    private final ServiceCreateContextRequest request;

    @With
    private final Sequences sequences;

    @Builder.Default
    @With
    private final Verifications verifications = new Verifications();

    public Channel getChannel() {
        return request.getChannel();
    }

    public String getChannelId() {
        return request.getChannelId();
    }

    public Activity getActivity() {
        return request.getActivity();
    }

    public String getActivityName() {
        return request.getActivityName();
    }

    public Identity getIdentity() {
        return request.getIdentity();
    }

    public IdvId getIdvId() {
        return request.getIdvId();
    }

    public Aliases getAliases() {
        return request.getAliases();
    }

    public Collection<String> getAliasTypes() {
        return request.getAliasTypes();
    }

    public boolean isProtectSensitiveData() {
        return request.isProtectSensitiveData();
    }

    public boolean isEligible() {
        return sequences.isEligible();
    }

    public boolean isComplete() {
        return sequences.isComplete(verifications);
    }

    public boolean isSuccessful() {
        return sequences.isSuccessful(verifications);
    }

    public Duration getDuration() {
        return sequences.getDuration();
    }

    public boolean hasExpired(Instant timestamp) {
        return timestamp.isAfter(expiry);
    }

    public Methods getNextMethods(String methodName) {
        return getNextMethods().getByName(methodName);
    }

    public Methods getNextMethods() {
        return sequences.getNextMethods(verifications);
    }

    public Context updateMethods(UnaryOperator<Method> function) {
        return withSequences(sequences.updateMethods(function));
    }

    public Context add(Verification verification) {
        String methodName = verification.getMethodName();
        if (isNextMethod(methodName)) {
            log.info("adding verification {} to context {} for method {}", verification.getId(), id, methodName);
            return withVerifications(verifications.add(verification));
        }
        throw new NotNextMethodException(methodName);
    }

    public CompleteVerificationResponse completeVerification(CompleteVerificationRequest request) {
        Verifications completed = verifications.complete(request);
        return CompleteVerificationResponse.builder()
                .original(this)
                .updated(withVerifications(completed))
                .verification(completed.getById(request.getId()))
                .build();
    }

    public Verification getVerification(UUID id) {
        return verifications.getById(id);
    }

    public boolean hasMoreCompletedSequencesThan(Context original) {
        return getCompletedSequenceCount() > original.getCompletedSequenceCount();
    }

    public boolean hasMoreCompletedMethodsThan(Context original) {
        return getCompletedMethodCount() > original.getCompletedMethodCount();
    }

    private long getCompletedSequenceCount() {
        return sequences.completedSequenceCount(verifications);
    }

    private long getCompletedMethodCount() {
        return sequences.completedMethodCount(verifications);
    }

    private boolean isNextMethod(String methodName) {
        Collection<String> nextMethodNames = sequences.getNextMethodNames(verifications);
        return nextMethodNames.contains(methodName);
    }

}
