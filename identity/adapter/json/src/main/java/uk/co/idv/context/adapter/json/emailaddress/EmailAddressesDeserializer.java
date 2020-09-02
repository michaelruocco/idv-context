package uk.co.idv.context.adapter.json.emailaddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class EmailAddressesDeserializer extends StdDeserializer<EmailAddresses> {

    public EmailAddressesDeserializer() {
        super(EmailAddresses.class);
    }

    @Override
    public EmailAddresses deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new EmailAddresses(JsonNodeConverter.toStringCollection(node, parser));
    }

}
