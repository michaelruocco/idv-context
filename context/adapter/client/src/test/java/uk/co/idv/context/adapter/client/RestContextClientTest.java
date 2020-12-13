package uk.co.idv.context.adapter.client;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequestMother;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequestMother;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequest;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequestMother;
import uk.co.idv.context.adapter.client.request.RequestConverter;
import uk.co.idv.context.adapter.client.request.UriFactory;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RestContextClientTest {

    private final RequestConverter requestConverter = mock(RequestConverter.class);
    private final ResponseConverter responseConverter = mock(ResponseConverter.class);
    private final RequestExecutor executor = mock(RequestExecutor.class);

    private final ContextClient client = RestContextClient.builder()
            .requestConverter(requestConverter)
            .responseConverter(responseConverter)
            .executor(executor)
            .build();

    @Test
    void shouldCreateContext() {
        ClientCreateContextRequest request = ClientCreateContextRequestMother.build();
        HttpRequest httpRequest = givenConvertsToHttpRequest(request);
        HttpResponse<String> httpResponse = givenExecutingRequestReturns(httpRequest);
        Context expectedContext = givenConvertsToContext(httpResponse);

        Context context = client.createContext(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldGetContext() {
        ClientGetContextRequest request = ClientGetContextRequestMother.build();
        HttpRequest httpRequest = givenConvertsToHttpRequest(request);
        HttpResponse<String> httpResponse = givenExecutingRequestReturns(httpRequest);
        Context expectedContext = givenConvertsToContext(httpResponse);

        Context context = client.getContext(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldRecordResult() {
        ClientRecordContextResultRequest request = ClientRecordContextResultRequestMother.build();
        HttpRequest httpRequest = givenConvertsToHttpRequest(request);
        HttpResponse<String> httpResponse = givenExecutingRequestReturns(httpRequest);
        Context expectedContext = givenConvertsToContext(httpResponse);

        Context context = client.recordResult(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    private HttpRequest givenConvertsToHttpRequest(ClientCreateContextRequest request) {
        HttpRequest httpRequest = buildHttpRequest();
        given(requestConverter.toPostHttpRequest(request)).willReturn(httpRequest);
        return httpRequest;
    }

    private HttpRequest givenConvertsToHttpRequest(ClientGetContextRequest request) {
        HttpRequest httpRequest = buildHttpRequest();
        given(requestConverter.toGetHttpRequest(request)).willReturn(httpRequest);
        return httpRequest;
    }

    private HttpRequest givenConvertsToHttpRequest(ClientRecordContextResultRequest request) {
        HttpRequest httpRequest = buildHttpRequest();
        given(requestConverter.toPatchHttpRequest(request)).willReturn(httpRequest);
        return httpRequest;
    }

    private HttpResponse<String> givenExecutingRequestReturns(HttpRequest httpRequest) {
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        given(executor.execute(httpRequest)).willReturn(httpResponse);
        return httpResponse;
    }

    private Context givenConvertsToContext(HttpResponse<String> httpResponse) {
        Context context = ContextMother.build();
        given(responseConverter.toContextOrThrowError(httpResponse)).willReturn(context);
        return context;
    }

    private static HttpRequest buildHttpRequest() {
        return HttpRequest.newBuilder()
                .uri(UriFactory.toUri("http://localhost:8080/text"))
                .build();
    }

}
