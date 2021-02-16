package uk.co.idv.context.adapter.client;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.mruoc.json.JsonConverter;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ResponseConverterTest {

    private static final String BODY = "response-body";

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final ResponseConverter converter = new ResponseConverter(jsonConverter);

    @Test
    void shouldReturnVerificationIfStatusCodeIs200() {
        HttpResponse<String> response = givenResponse(200);
        Verification expectedVerification = VerificationMother.incomplete();
        givenResponseBodyConvertsTo(expectedVerification, Verification.class);

        Verification verification = converter.toVerificationOrThrowError(response);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnVerificationIfStatusCodeIs299() {
        HttpResponse<String> response = givenResponse(299);
        Verification expectedVerification = VerificationMother.incomplete();
        givenResponseBodyConvertsTo(expectedVerification, Verification.class);

        Verification verification = converter.toVerificationOrThrowError(response);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldThrowExceptionIfStatusCodeIsBelow2XXRange() {
        HttpResponse<String> response = givenResponse(199);
        ApiError expectedError = mock(ApiError.class);
        givenResponseBodyConvertsTo(expectedError, ApiError.class);

        ApiErrorClientException exception = catchThrowableOfType(
                () -> converter.toVerificationOrThrowError(response),
                ApiErrorClientException.class
        );

        assertThat(exception.getError()).isEqualTo(expectedError);
    }

    @Test
    void shouldThrowExceptionIfStatusCodeIsAbove2XXRange() {
        HttpResponse<String> response = givenResponse(300);
        ApiError expectedError = mock(ApiError.class);
        givenResponseBodyConvertsTo(expectedError, ApiError.class);

        ApiErrorClientException exception = catchThrowableOfType(
                () -> converter.toVerificationOrThrowError(response),
                ApiErrorClientException.class
        );

        assertThat(exception.getError()).isEqualTo(expectedError);
    }

    private HttpResponse<String> givenResponse(int statusCode) {
        HttpResponse<String> response = mock(HttpResponse.class);
        given(response.body()).willReturn(BODY);
        given(response.statusCode()).willReturn(statusCode);
        return response;
    }

    private <T> void givenResponseBodyConvertsTo(T object, Class<T> type) {
        given(jsonConverter.toObject(BODY, type)).willReturn(object);
    }

}
