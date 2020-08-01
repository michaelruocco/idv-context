package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;

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
        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        addDeserializer(VerificationAttempt.class, new VerificationAttemptDeserializer());
    }

    private void setUpVerificationAttempts() {
        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());
        addDeserializer(VerificationAttempts.class, new VerificationAttemptsDeserializer());
    }

}
