package uk.co.idv.context.adapter.json.error.aliastype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.json.error.ApiError;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class UnsupportedAliasTypeError implements ApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Unsupported alias type";

    private final String type;

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
        return type;
    }

    @Override
    public Map<String, Object> getMeta() {
        return Collections.emptyMap();
    }

}
