package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.exception.ClientException;
import uk.co.idv.context.adapter.client.logger.BodyLoggingSubscriber;
import uk.co.mruoc.json.JsonConverter;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

        Throwable error = catchThrowable(() -> invalidUriConverter.toPostHttpRequest(request));

        assertThat(error)
                .isInstanceOf(ClientException.class)
                .hasCauseInstanceOf(URISyntaxException.class)
                .hasMessageEndingWith("Illegal character in authority at index 7: http://^/v1/contexts");
    }

    @Test
    void shouldConvertCreateRequestToHttpPostRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("POST");
    }

    @Test
    void shouldSetContentTypeHeaderOnHttpPostRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Content-Type")).contains("application/json");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpPostRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpPostRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts", BASE_URI);
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
    }

    @Test
    void shouldSetBodyOnHttpPostRequest() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPostHttpRequest(request);

        assertThat(httpRequest.bodyPublisher()).isPresent();
        assertThat(extractBody(httpRequest.bodyPublisher().get())).isEqualTo(REQUEST_BODY);
    }

    @Test
    void shouldConvertGetRequestToHttpGetRequest() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();

        HttpRequest httpRequest = converter.toGetHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("GET");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpGetRequest() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();

        HttpRequest httpRequest = converter.toGetHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpGetRequest() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();

        HttpRequest httpRequest = converter.toGetHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts/%s", BASE_URI, request.getId());
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
    }

    @Test
    void shouldConvertRecordRequestToHttpPatchRequest() {
        ClientRecordContextResultRequest request = ClientRecordContextResultRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchHttpRequest(request);

        assertThat(httpRequest.method()).isEqualTo("PATCH");
    }

    @Test
    void shouldSetContentTypeHeaderOnHttpPatchRequest() {
        ClientRecordContextResultRequest request = ClientRecordContextResultRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Content-Type")).contains("application/json");
    }

    @Test
    void shouldSetAcceptHeaderOnHttpPatchRequest() {
        ClientRecordContextResultRequest request = ClientRecordContextResultRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchHttpRequest(request);

        assertThat(httpRequest.headers().firstValue("Accept")).contains("application/json");
    }

    @Test
    void shouldSetUriOnHttpPatchRequest() {
        ClientRecordContextResultRequest request = ClientRecordContextResultRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchHttpRequest(request);

        String expectedUri = String.format("%s/v1/contexts/results", BASE_URI);
        assertThat(httpRequest.uri().toASCIIString()).isEqualTo(expectedUri);
    }

    @Test
    void shouldSetBodyOnHttpPatchRequest() {
        ClientRecordContextResultRequest request = ClientRecordContextResultRequestMother.build();
        given(jsonConverter.toJson(request.getBody())).willReturn(REQUEST_BODY);

        HttpRequest httpRequest = converter.toPatchHttpRequest(request);

        assertThat(httpRequest.bodyPublisher()).isPresent();
        assertThat(extractBody(httpRequest.bodyPublisher().get())).isEqualTo(REQUEST_BODY);
    }

    private static String extractBody(HttpRequest.BodyPublisher publisher) {
        try {
            HttpResponse.BodySubscriber<String> subscriber = HttpResponse.BodySubscribers.ofString(UTF_8);
            publisher.subscribe(new BodyLoggingSubscriber(subscriber));
            return subscriber.getBody().toCompletableFuture().get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
