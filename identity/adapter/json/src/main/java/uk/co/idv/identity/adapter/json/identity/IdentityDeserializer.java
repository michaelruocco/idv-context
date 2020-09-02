package uk.co.idv.identity.adapter.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.DefaultIdentity;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Optional;

public class IdentityDeserializer extends StdDeserializer<Identity> {

    protected IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultIdentity.builder()
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

    private static Aliases toAliases(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class);
    }

    private static PhoneNumbers toPhoneNumbers(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("phoneNumbers"))
                .map(phoneNumbers -> JsonNodeConverter.toObject(phoneNumbers, parser, PhoneNumbers.class))
                .orElseGet(PhoneNumbers::new);
    }

    private static EmailAddresses toEmailAddresses(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("emailAddresses"))
                .map(emailAddresses -> JsonNodeConverter.toObject(emailAddresses, parser, EmailAddresses.class))
                .orElseGet(EmailAddresses::new);
    }

}
