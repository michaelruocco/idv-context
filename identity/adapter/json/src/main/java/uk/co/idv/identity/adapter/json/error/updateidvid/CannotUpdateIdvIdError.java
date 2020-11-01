package uk.co.idv.identity.adapter.json.error.updateidvid;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;
import uk.co.idv.identity.entities.alias.IdvId;
import java.util.Map;

@Getter
public class CannotUpdateIdvIdError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Cannot update idv id";

    private final IdvId updated;
    private final IdvId existing;

    @Builder
    public CannotUpdateIdvIdError(IdvId updated, IdvId existing) {
        super(STATUS, TITLE, toMessage(updated, existing), toMap(updated, existing));
        this.updated = updated;
        this.existing = existing;
    }

    private static String toMessage(IdvId updated, IdvId existing) {
        return String.format("attempted to update existing value %s to %s",
                existing.getValue(),
                updated.getValue()
        );
    }

    private static Map<String, Object> toMap(IdvId updated, IdvId existing) {
        return Map.of(
                "new", updated.getValue(),
                "existing", existing.getValue()
        );
    }

}
