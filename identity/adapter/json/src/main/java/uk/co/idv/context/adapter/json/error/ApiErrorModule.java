package uk.co.idv.context.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.error.aliastype.UnsupportedAliasTypeError;
import uk.co.idv.context.adapter.json.error.aliastype.UnsupportedAliasTypeErrorMixin;
import uk.co.idv.context.adapter.json.error.country.mismatch.CountryMismatchError;
import uk.co.idv.context.adapter.json.error.country.mismatch.CountryMismatchErrorMixin;
import uk.co.idv.context.adapter.json.error.identitynotfound.IdentityNotFoundError;
import uk.co.idv.context.adapter.json.error.identitynotfound.IdentityNotFoundErrorMixin;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdError;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorMixin;

public class ApiErrorModule extends SimpleModule {

    public ApiErrorModule() {
        super("error-module", Version.unknownVersion());

        setMixInAnnotation(ApiError.class, ApiErrorMixin.class);
        setMixInAnnotation(CannotUpdateIdvIdError.class, CannotUpdateIdvIdErrorMixin.class);
        setMixInAnnotation(UnsupportedAliasTypeError.class, UnsupportedAliasTypeErrorMixin.class);
        setMixInAnnotation(IdentityNotFoundError.class, IdentityNotFoundErrorMixin.class);
        setMixInAnnotation(CountryMismatchError.class, CountryMismatchErrorMixin.class);
    }

}
