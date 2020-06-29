package uk.co.idv.context.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdError;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorMixin;

public class ApiErrorModule extends SimpleModule {

    public ApiErrorModule() {
        super("error-module", Version.unknownVersion());
        setMixInAnnotation(ApiError.class, ApiErrorMixin.class);
        setMixInAnnotation(CannotUpdateIdvIdError.class, CannotUpdateIdvIdErrorMixin.class);
    }

}
