package uk.co.idv.context.adapter.json.error.country;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.adapter.json.error.DefaultApiError;

import java.util.Map;

@Builder
@Getter
public class CountryMismatchError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Cannot merge identities if countries do not match";

    private final CountryCode updated;
    private final CountryCode existing;

    public CountryMismatchError(CountryCode updated, CountryCode existing) {
        super(STATUS, TITLE, toMessage(updated, existing), toMap(updated, existing));
        this.updated = updated;
        this.existing = existing;
    }

    private static String toMessage(CountryCode updated, CountryCode existing) {
        return String.format("attempted to merge identity from %s to %s",
                existing,
                updated
        );
    }

    private static Map<String, Object> toMap(CountryCode updated, CountryCode existing) {
        return Map.of(
                "new", updated,
                "existing", existing
        );
    }

}
