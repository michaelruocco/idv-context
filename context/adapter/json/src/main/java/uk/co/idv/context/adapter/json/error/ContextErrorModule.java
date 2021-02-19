package uk.co.idv.context.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.adapter.json.error.MethodErrorModule;

import java.util.Arrays;

public class ContextErrorModule extends SimpleModule {

    public ContextErrorModule() {
        super("context-error-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new MethodErrorModule(),
                new JavaTimeModule()
        );
    }

}
