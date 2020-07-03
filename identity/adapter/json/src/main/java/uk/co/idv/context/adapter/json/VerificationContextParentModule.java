package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.eligibility.EligibilityModule;
import uk.co.idv.context.adapter.json.error.ApiErrorModule;
import uk.co.idv.context.adapter.json.identity.IdentityModule;

import java.util.Arrays;

public class VerificationContextParentModule extends SimpleModule {

    public VerificationContextParentModule() {
        super("verification-context-json-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new IdentityModule(),
                new EligibilityModule(),
                new ApiErrorModule()
        );
    }

}
