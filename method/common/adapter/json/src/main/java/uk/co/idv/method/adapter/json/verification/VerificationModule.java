package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.activity.adapter.json.ActivityModule;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.method.MethodModule;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.Verifications;

import java.util.Arrays;

public class VerificationModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public VerificationModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public VerificationModule(MethodMappings mappings) {
        super("verification-module", Version.unknownVersion());
        this.mappings = mappings;

        addDeserializer(CreateVerificationRequest.class, new CreateVerificationRequestDeserializer());
        addDeserializer(CompleteVerificationRequest.class, new CompleteVerificationRequestDeserializer());

        setUpCompleteVerificationResult();
        setUpVerifications();
    }

    private void setUpCompleteVerificationResult() {
        setMixInAnnotation(CompleteVerificationResult.class, CompleteVerificationResultMixin.class);
        addDeserializer(CompleteVerificationResult.class, new CompleteVerificationResultDeserializer());
    }

    private void setUpVerifications() {
        setMixInAnnotation(Verifications.class, VerificationsMixin.class);
        addDeserializer(Verifications.class, new VerificationsDeserializer());
        addDeserializer(Verification.class, new VerificationDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ActivityModule(),
                new MethodModule(mappings),
                new Jdk8Module()
        );
    }

}
