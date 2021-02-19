package uk.co.idv.method.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.common.adapter.json.error.ErrorModule;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredErrorMixin;

import java.util.Collections;

public class MethodErrorModule extends SimpleModule {

    public MethodErrorModule() {
        super("method-error-module", Version.unknownVersion());

        setMixInAnnotation(ContextExpiredError.class, ContextExpiredErrorMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new ErrorModule());
    }

}
