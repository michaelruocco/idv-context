package uk.co.idv.identity.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.adapter.json.emailaddress.EmailAddressExtractor;
import uk.co.idv.identity.adapter.json.phonenumber.PhoneNumberExtractor;
import uk.co.idv.identity.entities.channel.gb.Abc;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class AbcChannelDeserializer extends StdDeserializer<Abc> {

    public AbcChannelDeserializer() {
        super(Abc.class);
    }

    @Override
    public Abc deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Abc.builder()
                .emailAddresses(EmailAddressExtractor.toEmailAddresses(node, parser))
                .phoneNumbers(PhoneNumberExtractor.toPhoneNumbers(node, parser))
                .validCookie(node.get("validCookie").asBoolean())
                .build();
    }

}
