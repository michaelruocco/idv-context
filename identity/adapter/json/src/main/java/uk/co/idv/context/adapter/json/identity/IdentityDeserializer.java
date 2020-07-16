package uk.co.idv.context.adapter.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.util.Optional;

import static uk.co.idv.context.adapter.json.JsonNodeConverter.toObject;

public class IdentityDeserializer extends StdDeserializer<Identity> {

    protected IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Identity.builder()
                .country(toCountry(node))
                .aliases(toAliases(node, parser))
                .phoneNumbers(toPhoneNumbers(node, parser))
                .emailAddresses(toEmailAddresses(node, parser))
                .build();
    }

    private static CountryCode toCountry(JsonNode node) {
        return Optional.ofNullable(node.get("country"))
                .map(country -> CountryCode.getByCode(country.asText()))
                .orElse(null);
    }

    private static Aliases toAliases(final JsonNode node, final JsonParser parser) {
        return toObject(node.get("aliases"), parser, Aliases.class);
    }

    private static PhoneNumbers toPhoneNumbers(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("phoneNumbers"))
                .map(phoneNumbers -> toObject(phoneNumbers, parser, PhoneNumbers.class))
                .orElseGet(PhoneNumbers::new);
    }

    private static EmailAddresses toEmailAddresses(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("emailAddresses"))
                .map(emailAddresses -> toObject(emailAddresses, parser, EmailAddresses.class))
                .orElseGet(EmailAddresses::new);
    }

}
