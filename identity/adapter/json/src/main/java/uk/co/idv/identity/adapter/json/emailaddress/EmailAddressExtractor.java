package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

import java.util.Optional;

public interface EmailAddressExtractor {

    static EmailAddresses toEmailAddresses(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("emailAddresses"))
                .map(emailAddresses -> JsonNodeConverter.toObject(emailAddresses, parser, EmailAddresses.class))
                .orElseGet(EmailAddresses::new);
    }

}
