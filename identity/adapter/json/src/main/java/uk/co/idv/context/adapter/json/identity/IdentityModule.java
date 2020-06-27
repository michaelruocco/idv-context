package uk.co.idv.context.adapter.json.identity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.alias.AliasModule;
import uk.co.idv.context.adapter.json.emailaddress.EmailAddressModule;
import uk.co.idv.context.adapter.json.phonenumber.PhoneNumberModule;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Arrays;

public class IdentityModule extends SimpleModule {

    public IdentityModule() {
        super("identity-module", Version.unknownVersion());
        setUpIdentity();
    }

    private void setUpIdentity() {
        setMixInAnnotation(Identity.class, IdentityMixin.class);
        addDeserializer(Identity.class, new IdentityDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new AliasModule(),
                new PhoneNumberModule(),
                new EmailAddressModule()
        );
    }

}
