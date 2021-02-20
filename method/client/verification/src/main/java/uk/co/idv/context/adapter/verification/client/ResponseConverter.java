package uk.co.idv.context.adapter.verification.client;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.mruoc.json.JsonConverter;

import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class ResponseConverter {

    private final JsonConverter jsonConverter;

    public Verification toVerificationOrThrowError(HttpResponse<String> response) {
        return toTypeOrThrowError(response, Verification.class);
    }

    public <T> T toTypeOrThrowError(HttpResponse<String> response, Class<T> type) {
        if (isSuccessful(response)) {
            return jsonConverter.toObject(response.body(), type);
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
