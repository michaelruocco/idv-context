package uk.co.idv.common.adapter.json.error.internalserver;

import uk.co.idv.common.adapter.json.error.ApiError;

public interface InternalServerErrorMother {

    static ApiError internalServerError() {
        return new InternalServerError("error message");
    }

}
