package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.util.Collections;

public class VerificationAttemptModule extends SimpleModule {

    public VerificationAttemptModule() {
        super("verification-attempt-module", Version.unknownVersion());
        setUpVerificationAttempt();
        setUpVerificationAttempts();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

    private void setUpVerificationAttempt() {
        setMixInAnnotation(Attempt.class, VerificationAttemptMixin.class);
        addDeserializer(Attempt.class, new VerificationAttemptDeserializer());
    }

    private void setUpVerificationAttempts() {
        addSerializer(Attempts.class, new VerificationAttemptsSerializer());
        addDeserializer(Attempts.class, new VerificationAttemptsDeserializer());
    }

}
