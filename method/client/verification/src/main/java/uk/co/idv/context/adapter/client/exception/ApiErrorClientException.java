package uk.co.idv.context.adapter.client.exception;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.ApiError;

@Getter
public class ApiErrorClientException extends ClientException {

    private final transient ApiError error;

    public ApiErrorClientException(ApiError error) {
        super(error.getMessage());
        this.error = error;
    }

}
