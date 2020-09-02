package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.channel.ChannelModule;
import uk.co.idv.context.adapter.json.identity.IdentityModule;
import uk.co.idv.identity.entities.eligibility.Eligibility;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;

import java.util.Arrays;

public class EligibilityModule extends SimpleModule {

    public EligibilityModule() {
        super("eligibility-module", Version.unknownVersion());
        setUpCreateRequest();
        setUpEligibility();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new IdentityModule(),
                new ChannelModule()
        );
    }

    private void setUpCreateRequest() {
        setMixInAnnotation(CreateEligibilityRequest.class, CreateEligibilityRequestMixin.class);
        addDeserializer(CreateEligibilityRequest.class, new CreateEligibilityRequestDeserializer());
    }

    private void setUpEligibility() {
        addDeserializer(Eligibility.class, new EligibilityDeserializer());
    }

}
