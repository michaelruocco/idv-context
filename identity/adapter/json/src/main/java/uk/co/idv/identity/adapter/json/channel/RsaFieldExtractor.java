package uk.co.idv.identity.adapter.json.channel;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class RsaFieldExtractor {

    private static final String ISSUER_SESSION_ID = "issuerSessionId";
    private static final String DS_SESSION_ID = "dsSessionId";

    private RsaFieldExtractor() {
        // utility class
    }

    public static UUID issuerSessionId(JsonNode node) {
        return extractOptionalUuid(ISSUER_SESSION_ID, node);
    }

    public static UUID deSessionId(JsonNode node) {
        return extractOptionalUuid(DS_SESSION_ID, node);
    }

    private static UUID extractOptionalUuid(String name, JsonNode node) {
        if (node.has(name)) {
            return UUID.fromString(node.get(name).asText());
        }
        return null;
    }

}
