package uk.co.idv.context.adapter.json.error.aliastype;

import uk.co.idv.context.adapter.json.error.ApiError;

public interface UnsupportedAliasTypeErrorMother {

    static ApiError unsupportedAliasTypeError() {
        return new UnsupportedAliasTypeError("my-type");
    }

}
