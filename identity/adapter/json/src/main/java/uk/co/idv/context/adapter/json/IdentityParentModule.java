package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.eligibility.EligibilityModule;
import uk.co.idv.context.adapter.json.error.IdentityErrorModule;
import uk.co.idv.context.adapter.json.identity.IdentityModule;

import java.util.Arrays;

public class IdentityParentModule extends SimpleModule {

    public IdentityParentModule() {
        super("identity-parent-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new IdentityModule(),
                new IdentityErrorModule(),
                new EligibilityModule()
        );
    }

}
