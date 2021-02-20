package uk.co.idv.context.adapter.verification.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.exception.ClientException;
import uk.co.mruoc.json.JsonConverter;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.adapter.verification.client.request.BodyExtractor.extractBody;

class RequestConverterTest {

    private static final String REQUEST_BODY = "my-request-body";
    private static final String BASE_URI = "http://localhost:8081";

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final RequestConverter converter = RequestConverter.builder()
            .baseUri(BASE_URI)
            .jsonConverter(jsonConverter)
            .build();

    @Test
    void shouldThrowExceptionIfBaseUrlIsInvalid() {
        RequestConverter invalidConverter = RequestConverter.builder()
                .baseUri(BASE_URI + " ")
                .jsonConverter(jsonConverter)
                .build();
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();

        Throwable error = catchThrowable(() -> invalidConverter.toPostVerificationHttpRequest(request));

        assertThat(error)
                .isInstanceOf(ClientException.class)
                .hasCauseInstanceOf(URISyntaxException.class)
                .hasMessage("java.net.URISyntaxException: Illegal character in authority at index 7: " +
                        "http://localhost:8081 /v1/contexts/verifications");
    }

    @Test
    void shouldConvertCreateVerificationRequestToHttpPostVerificationRequest() {
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostVerificationHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("POST");
    }

    @Test
    void shouldSetContentTypeHeaderOnHttpPostVerificationRequest() {
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostVerificationHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Content-Type")).contains("application/json");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpPostVerificationRequest() {
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostVerificationHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpPostVerificationRequest() {
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostVerificationHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts/verifications", BASE_URI);
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
    }

    @Test
    void shouldSetBodyOnHttpPostVerificationRequest() {
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostVerificationHttpRequest(request);

        assertThat(httpRequest.bodyPublisher()).isPresent();
        assertThat(extractBody(httpRequest.bodyPublisher().get())).isEqualTo(REQUEST_BODY);
    }

    @Test
    void shouldConvertCompleteVerificationRequestToHttpPatchVerificationRequest() {
        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchVerificationHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("PATCH");
    }

    @Test
    void shouldSetContentTypeHeaderOnHttpPatchVerificationRequest() {
        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchVerificationHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Content-Type")).contains("application/json");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpPatchVerificationRequest() {
        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchVerificationHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpPatchVerificationRequest() {
        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchVerificationHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts/verifications", BASE_URI);
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
    }

    @Test
    void shouldSetBodyOnHttpPatchVerificationRequest() {
        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchVerificationHttpRequest(request);

        assertThat(httpRequest.bodyPublisher()).isPresent();
        assertThat(extractBody(httpRequest.bodyPublisher().get())).isEqualTo(REQUEST_BODY);
    }

}
