package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.context.adapter.json.context.create.CreateContextModule;
import uk.co.idv.context.adapter.json.context.sequence.SequenceModule;
import uk.co.idv.context.entities.context.Context;

import java.util.Arrays;

public class ContextModule extends SimpleModule {

    public ContextModule() {
        super("context-module", Version.unknownVersion());

        setMixInAnnotation(Context.class, ContextMixin.class);

        addDeserializer(Context.class, new ContextDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new CreateContextModule(),
                new SequenceModule(),
                new JavaTimeModule()
        );
    }

}
