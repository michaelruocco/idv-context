package uk.co.idv.context.adapter.json.error.internalserver;

import uk.co.idv.context.adapter.json.error.ApiError;

public interface InternalServerErrorMother {

    static ApiError internalServerError() {
        return new InternalServerError("error message");
    }

}
