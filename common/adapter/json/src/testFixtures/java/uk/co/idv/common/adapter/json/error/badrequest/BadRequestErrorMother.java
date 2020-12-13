package uk.co.idv.common.adapter.json.error.badrequest;

import uk.co.idv.common.adapter.json.error.ApiError;

import java.util.Map;

public interface BadRequestErrorMother {

    static ApiError badRequestErrorWithMeta() {
        Map<String, Object> meta = Map.of(
                "key1", "value1",
                "key2", "value2"
        );
        return new BadRequestError("error with meta", meta);
    }

}
