package uk.co.idv.common.adapter.json.error;

import java.util.Map;

public interface ApiError {

    int getStatus();

    String getTitle();

    String getMessage();

    Map<String, Object> getMeta();

}
