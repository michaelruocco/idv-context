package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.entities.context.sequence.nextmethods.AnyOrderNextMethodsPolicy;
import uk.co.idv.context.entities.context.sequence.nextmethods.InOrderNextMethodsPolicy;
import uk.co.idv.context.entities.context.sequence.nextmethods.NextMethodsPolicy;

public class NextMethodsPolicyModule extends SimpleModule {

    public NextMethodsPolicyModule() {
        super("next-methods-policy-module", Version.unknownVersion());

        addDeserializer(NextMethodsPolicy.class, new NextMethodsPolicyDeserializer());
        addDeserializer(InOrderNextMethodsPolicy.class, new InOrderNextMethodsPolicyDeserializer());
        addDeserializer(AnyOrderNextMethodsPolicy.class, new AnyOrderNextMethodsPolicyDeserializer());
    }

}
