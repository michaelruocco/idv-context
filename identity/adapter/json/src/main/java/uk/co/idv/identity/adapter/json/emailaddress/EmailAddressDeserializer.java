package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class EmailAddressDeserializer extends StdDeserializer<EmailAddress> {

    protected EmailAddressDeserializer() {
        super(EmailAddress.class);
    }

    @Override
    public EmailAddress deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new EmailAddress(node.asText());
    }

}
