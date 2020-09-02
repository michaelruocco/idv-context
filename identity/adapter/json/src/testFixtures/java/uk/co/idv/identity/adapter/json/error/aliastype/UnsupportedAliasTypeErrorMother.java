package uk.co.idv.identity.adapter.json.error.aliastype;

import uk.co.idv.common.adapter.json.error.ApiError;

public interface UnsupportedAliasTypeErrorMother {

    static ApiError unsupportedAliasTypeError() {
        return new UnsupportedAliasTypeError("my-type");
    }

}
