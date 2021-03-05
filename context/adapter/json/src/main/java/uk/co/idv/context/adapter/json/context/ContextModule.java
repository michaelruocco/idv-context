package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.context.adapter.json.context.create.CreateContextModule;
import uk.co.idv.context.adapter.json.context.sequence.SequenceModule;
import uk.co.idv.context.adapter.json.error.ContextErrorModule;
import uk.co.idv.context.entities.context.ApiContext;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.verification.VerificationModule;

import java.util.Arrays;

public class ContextModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public ContextModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public ContextModule(MethodMappings mappings) {
        super("context-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(ApiContext.class, ApiContextMixin.class);
        setMixInAnnotation(Context.class, ContextMixin.class);

        addDeserializer(Context.class, new ContextDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ContextErrorModule(),
                new SequenceModule(mappings),
                new CreateContextModule(mappings),
                new VerificationModule(mappings),
                new JavaTimeModule()
        );
    }

}
