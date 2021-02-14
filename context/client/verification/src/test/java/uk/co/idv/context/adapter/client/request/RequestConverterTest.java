package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.mruoc.json.JsonConverter;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.adapter.client.request.BodyExtractor.extractBody;

class RequestConverterTest {

    private static final String REQUEST_BODY = "my-request-body";
    private static final String BASE_URI = "http://localhost:8081";

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final RequestConverter converter = RequestConverter.builder()
            .baseUri(BASE_URI)
            .jsonConverter(jsonConverter)
            .build();

    @Test
    void shouldThrowExceptionIfBaseUriIsNotCorrectUriFormat() {
        RequestConverter invalidUriConverter = RequestConverter.builder()
                .baseUri("http://^")
                .build();
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();

        Throwable error = catchThrowable(() -> invalidUriConverter.toPostContextHttpRequest(request));

        assertThat(error)
                .isInstanceOf(ClientException.class)
                .hasCauseInstanceOf(URISyntaxException.class)
                .hasMessageEndingWith("Illegal character in authority at index 7: http://^/v1/contexts");
    }

    @Test
    void shouldConvertCreateRequestToHttpPostContextRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostContextHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("POST");
    }

    @Test
    void shouldSetContentTypeHeaderOnHttpPostContextRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostContextHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Content-Type")).contains("application/json");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpPostContextRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostContextHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpPostContextRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostContextHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts", BASE_URI);
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
    }

    @Test
    void shouldSetBodyOnHttpPostContextRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostContextHttpRequest(request);

        assertThat(httpRequest.bodyPublisher()).isPresent();
        assertThat(extractBody(httpRequest.bodyPublisher().get())).isEqualTo(REQUEST_BODY);
    }

    @Test
    void shouldConvertGetRequestToHttpGetContextRequest() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();

        HttpRequest httpRequest = converter.toGetContextHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("GET");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpGetContextRequest() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();

        HttpRequest httpRequest = converter.toGetContextHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpGetContextRequest() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();

        HttpRequest httpRequest = converter.toGetContextHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts/%s", BASE_URI, request.getId());
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
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
