package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import lombok.Getter;
import uk.co.idv.context.adapter.json.error.DefaultApiError;

import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
public class EligibilityNotConfiguredError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Eligibility not configured";

    public EligibilityNotConfiguredError(String channelId) {
        super(STATUS, TITLE, toMessage(channelId), emptyMap());
    }

    private static String toMessage(String channelId) {
        return String.format("eligibility not configured for channel %s", channelId);
    }

    private static Map<String, Object> toMap(String channelId) {
        return Map.of("channelId", channelId);
    }

}
