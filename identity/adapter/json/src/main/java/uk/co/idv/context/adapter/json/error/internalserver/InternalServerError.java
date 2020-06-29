package uk.co.idv.context.adapter.json.error.internalserver;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.json.error.ApiError;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class InternalServerError implements ApiError {

    private final String message;

    @Override
    public int getStatus() {
        return 500;
    }

    @Override
    public String getTitle() {
        return "Internal server error";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Map<String, Object> getMeta() {
        return Collections.emptyMap();
    }
}
