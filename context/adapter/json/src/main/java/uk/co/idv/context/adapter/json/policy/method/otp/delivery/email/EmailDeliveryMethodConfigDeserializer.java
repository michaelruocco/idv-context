package uk.co.idv.context.adapter.json.policy.method.otp.delivery.email;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfig;

public class EmailDeliveryMethodConfigDeserializer extends StdDeserializer<EmailDeliveryMethodConfig> {

    public EmailDeliveryMethodConfigDeserializer() {
        super(EmailDeliveryMethodConfig.class);
    }

    @Override
    public EmailDeliveryMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        return new EmailDeliveryMethodConfig();
    }

}
