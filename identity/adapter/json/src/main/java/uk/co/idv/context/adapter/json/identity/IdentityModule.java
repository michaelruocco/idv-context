package uk.co.idv.context.adapter.json.identity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.alias.AliasModule;
import uk.co.idv.context.adapter.json.emailaddress.EmailAddressModule;
import uk.co.idv.context.adapter.json.phonenumber.PhoneNumberModule;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.Arrays;

public class IdentityModule extends SimpleModule {

    public IdentityModule() {
        super("identity-module", Version.unknownVersion());
        setUpIdentity();
        setUpRequestedData();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new AliasModule(),
                new PhoneNumberModule(),
                new EmailAddressModule()
        );
    }

    private void setUpIdentity() {
        setMixInAnnotation(Identity.class, IdentityMixin.class);
        addDeserializer(Identity.class, new IdentityDeserializer());
    }

    private void setUpRequestedData() {
        addDeserializer(RequestedData.class, new RequestedDataDeserializer());
    }

}
