package uk.co.idv.identity.adapter.json.error.internalserver;

import uk.co.idv.identity.adapter.json.error.ApiError;

public interface InternalServerErrorMother {

    static ApiError internalServerError() {
        return new InternalServerError("error message");
    }

}
