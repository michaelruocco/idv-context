package uk.co.idv.common.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ErrorModule extends SimpleModule {

    public ErrorModule() {
        super("error-module", Version.unknownVersion());

        setMixInAnnotation(ApiError.class, ApiErrorMixin.class);
    }

}
