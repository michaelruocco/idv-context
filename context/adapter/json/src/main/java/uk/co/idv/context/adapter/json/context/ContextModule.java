package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.context.adapter.json.context.create.CreateContextModule;
import uk.co.idv.context.adapter.json.context.sequence.SequenceModule;
import uk.co.idv.context.adapter.json.result.RecordRequestModule;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.adapter.json.MethodMapping;

import java.util.Arrays;
import java.util.Collection;

public class ContextModule extends SimpleModule {

    private final transient Collection<MethodMapping> mappings;

    public ContextModule(MethodMapping... mappings) {
        this(Arrays.asList(mappings));
    }

    public ContextModule(Collection<MethodMapping> mappings) {
        super("context-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(Context.class, ContextMixin.class);

        addDeserializer(Context.class, new ContextDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new SequenceModule(mappings),
                new CreateContextModule(mappings),
                new JavaTimeModule(),
                new RecordRequestModule()
        );
    }

}
