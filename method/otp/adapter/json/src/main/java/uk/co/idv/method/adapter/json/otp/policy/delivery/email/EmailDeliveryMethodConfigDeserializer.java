package uk.co.idv.method.adapter.json.otp.policy.delivery.email;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfig;

public class EmailDeliveryMethodConfigDeserializer extends StdDeserializer<EmailDeliveryMethodConfig> {

    public EmailDeliveryMethodConfigDeserializer() {
        super(EmailDeliveryMethodConfig.class);
    }

    @Override
    public EmailDeliveryMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        return new EmailDeliveryMethodConfig();
    }

}
