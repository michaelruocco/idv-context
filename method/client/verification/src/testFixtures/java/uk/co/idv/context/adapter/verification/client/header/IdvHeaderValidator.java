package uk.co.idv.context.adapter.verification.client.header;

import org.apache.commons.lang3.StringUtils;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.badrequest.header.AuthorizationHeaderNotProvidedError;
import uk.co.idv.common.adapter.json.error.badrequest.header.ChannelIdHeaderNotProvidedError;
import uk.co.idv.common.adapter.json.error.badrequest.header.CorrelationIdHeaderNotProvidedError;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;

import java.util.Optional;

public class IdvHeaderValidator {

    public void validate(IdvRequestHeaders headers) {
        Optional<ApiError> error = toError(headers);
        if (error.isEmpty()) {
            return;
        }
        throw new ApiErrorClientException(error.get());
    }

    private Optional<ApiError> toError(IdvRequestHeaders headers) {
        if (headers.getCorrelationId() == null) {
            return Optional.of(new CorrelationIdHeaderNotProvidedError());
        }
        if (StringUtils.isEmpty(headers.getChannelId())) {
            return Optional.of(new ChannelIdHeaderNotProvidedError());
        }
        if (headers.getAuthorization().isEmpty()) {
            return Optional.of(new AuthorizationHeaderNotProvidedError());
        }
        return Optional.empty();
    }

}
