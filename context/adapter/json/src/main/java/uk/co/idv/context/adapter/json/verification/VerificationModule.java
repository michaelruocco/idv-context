package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.context.adapter.json.activity.ActivityModule;
import uk.co.idv.context.adapter.json.context.sequence.SequenceModule;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;

import java.util.Arrays;

public class VerificationModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public VerificationModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public VerificationModule(MethodMappings mappings) {
        super("verification-module", Version.unknownVersion());
        this.mappings = mappings;

        addDeserializer(Verification.class, new VerificationDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ActivityModule(),
                new SequenceModule(mappings),
                new Jdk8Module()
        );
    }

}
