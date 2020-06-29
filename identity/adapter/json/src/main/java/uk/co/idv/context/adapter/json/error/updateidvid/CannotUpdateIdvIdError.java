package uk.co.idv.context.adapter.json.error.updateidvid;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.entities.alias.IdvId;
import java.util.Map;

@Builder
@Getter
public class CannotUpdateIdvIdError implements ApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Cannot update idv id";

    private final IdvId updated;
    private final IdvId existing;

    @Override
    public int getStatus() {
        return STATUS;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String getMessage() {
        return String.format("attempted to update existing value %s to %s",
                existing.getValue(),
                updated.getValue()
        );
    }

    @Override
    public Map<String, Object> getMeta() {
        return Map.of(
                "existing", existing.getValue(),
                "new", updated.getValue()
        );
    }

}
