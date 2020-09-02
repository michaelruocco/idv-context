package uk.co.idv.identity.adapter.json.error;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DefaultApiError implements ApiError {

    private final int status;
    private final String title;
    private final String message;
    private final Map<String, Object> meta;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Map<String, Object> getMeta() {
        return meta;
    }

}
