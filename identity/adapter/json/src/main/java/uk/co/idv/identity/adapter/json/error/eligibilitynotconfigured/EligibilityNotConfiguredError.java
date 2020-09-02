package uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured;

import lombok.Getter;
import uk.co.idv.identity.adapter.json.error.DefaultApiError;

import java.util.Map;

@Getter
public class EligibilityNotConfiguredError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Eligibility not configured";

    public EligibilityNotConfiguredError(String channelId) {
        super(STATUS, TITLE, toMessage(channelId), toMap(channelId));
    }

    private static String toMessage(String channelId) {
        return String.format("eligibility not configured for channel %s", channelId);
    }

    private static Map<String, Object> toMap(String channelId) {
        return Map.of("channelId", channelId);
    }

}
