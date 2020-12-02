package uk.co.idv.context.adapter.client;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.entities.context.Context;
import uk.co.mruoc.json.JsonConverter;

import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class ResponseConverter {

    private final JsonConverter jsonConverter;

    public Context toContextOrThrowError(HttpResponse<String> response) {
        if (isSuccessful(response)) {
            return jsonConverter.toObject(response.body(), Context.class);
        }
        throw extractException(response);
    }

    private boolean isSuccessful(HttpResponse<String> response) {
        int status = response.statusCode();
        return status >= 200 && status <= 299;
    }

    private RuntimeException extractException(HttpResponse<String> response) {
        ApiError error = jsonConverter.toObject(response.body(), ApiError.class);
        return new ApiErrorClientException(error);
    }

}
