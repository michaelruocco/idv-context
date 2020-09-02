package uk.co.idv.lockout.adapter.json.attempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.Collections;

public class AttemptModule extends SimpleModule {

    public AttemptModule() {
        super("verification-attempt-module", Version.unknownVersion());
        setUpAttempt();
        setUpAttempts();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

    private void setUpAttempt() {
        setMixInAnnotation(Attempt.class, AttemptMixin.class);
        addDeserializer(Attempt.class, new AttemptDeserializer());
    }

    private void setUpAttempts() {
        addSerializer(Attempts.class, new AttemptsSerializer());
        addDeserializer(Attempts.class, new AttemptsDeserializer());
    }

}
