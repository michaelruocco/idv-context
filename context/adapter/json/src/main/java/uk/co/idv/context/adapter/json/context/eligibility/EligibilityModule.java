package uk.co.idv.context.adapter.json.context.eligibility;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.util.Collections;

public class EligibilityModule extends SimpleModule {

    public EligibilityModule() {
        super("eligibility-module", Version.unknownVersion());

        addDeserializer(Eligibility.class, new EligibilityDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new Jdk8Module());
    }

}
