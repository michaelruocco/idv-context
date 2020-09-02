package uk.co.idv.identity.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.adapter.json.error.aliastype.UnsupportedAliasTypeError;
import uk.co.idv.identity.adapter.json.error.aliastype.UnsupportedAliasTypeErrorMixin;
import uk.co.idv.identity.adapter.json.error.country.mismatch.CountryMismatchError;
import uk.co.idv.identity.adapter.json.error.country.mismatch.CountryMismatchErrorMixin;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundError;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorMixin;
import uk.co.idv.identity.adapter.json.error.updateidvid.CannotUpdateIdvIdError;
import uk.co.idv.identity.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorMixin;

import java.util.Collections;

public class IdentityErrorModule extends SimpleModule {

    public IdentityErrorModule() {
        super("identity-error-module", Version.unknownVersion());

        setMixInAnnotation(CannotUpdateIdvIdError.class, CannotUpdateIdvIdErrorMixin.class);
        setMixInAnnotation(UnsupportedAliasTypeError.class, UnsupportedAliasTypeErrorMixin.class);
        setMixInAnnotation(IdentityNotFoundError.class, IdentityNotFoundErrorMixin.class);
        setMixInAnnotation(CountryMismatchError.class, CountryMismatchErrorMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new ErrorModule());
    }

}
