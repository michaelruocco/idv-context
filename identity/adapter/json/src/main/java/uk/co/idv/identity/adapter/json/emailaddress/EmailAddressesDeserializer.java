package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class EmailAddressesDeserializer extends StdDeserializer<EmailAddresses> {

    private static final TypeReference<Collection<EmailAddress>> EMAIL_ADDRESS_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public EmailAddressesDeserializer() {
        super(EmailAddresses.class);
    }

    @Override
    public EmailAddresses deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new EmailAddresses(JsonNodeConverter.toCollection(node, parser, EMAIL_ADDRESS_COLLECTION));
    }

}
