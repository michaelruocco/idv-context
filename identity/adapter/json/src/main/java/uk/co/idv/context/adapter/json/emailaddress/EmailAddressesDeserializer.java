package uk.co.idv.context.adapter.json.emailaddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;

public class EmailAddressesDeserializer extends StdDeserializer<EmailAddresses> {

    public EmailAddressesDeserializer() {
        super(EmailAddresses.class);
    }

    @Override
    public EmailAddresses deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new EmailAddresses(JsonNodeConverter.toStringCollection(node, parser));
    }

}
